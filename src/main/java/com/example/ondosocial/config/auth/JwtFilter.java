package com.example.ondosocial.config.auth;

import com.example.ondosocial.config.error.AuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI();

        if (url.startsWith("/sign")) {
            chain.doFilter(request, response);
            return;
        }

        String bearerJwt = httpRequest.getHeader(AUTHORIZATION);

        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            // JWT 유효성 검사와 claims 추출
            Claims claims = jwtUtil.extractClaims(jwt);

            // 사용자 정보를 ArgumentResolver 로 넘기기 위해 HttpServletRequest 에 세팅
            httpRequest.setAttribute("userId", Long.parseLong(claims.getSubject()));
            httpRequest.setAttribute("email", claims.get("email", String.class));

            chain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error(AuthErrorCode.INVALID_SIGNATURE.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthErrorCode.INVALID_SIGNATURE.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(AuthErrorCode.EXPIRED_TOKEN.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(AuthErrorCode.UNSUPPORTED_TOKEN.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AuthErrorCode.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(AuthErrorCode.EMPTY_CLAIMS.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AuthErrorCode.EMPTY_CLAIMS.getMessage());
        } catch (Exception e) {
            log.error(AuthErrorCode.TOKEN_VERIFICATION_ERROR.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthErrorCode.TOKEN_VERIFICATION_ERROR.getMessage());
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}