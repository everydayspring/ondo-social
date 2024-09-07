package com.example.ondosocial.config.auth;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.example.ondosocial.config.error.AuthErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final String AUTHORIZATION = "Authorization";
    private static final List<String> PERMIT_ALL_URIS = List.of("/signin", "/signup");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURI();

        if (!PERMIT_ALL_URIS.contains(url)) {

            String bearerJwt = request.getHeader(AUTHORIZATION);

            String jwt = jwtUtil.substringToken(bearerJwt);

            try {
                // JWT 유효성 검사와 claims 추출
                Claims claims = jwtUtil.extractClaims(jwt);

                // 사용자 정보를 ArgumentResolver 로 넘기기 위해 HttpServletRequest 에 세팅
                request.setAttribute("userId", Long.parseLong(claims.getSubject()));
                request.setAttribute("email", claims.get("email", String.class));

            } catch (SecurityException | MalformedJwtException e) {
                log.error(AuthErrorCode.INVALID_SIGNATURE.getMessage(), e);
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, AuthErrorCode.INVALID_SIGNATURE.getMessage());
            } catch (ExpiredJwtException e) {
                log.error(AuthErrorCode.EXPIRED_TOKEN.getMessage(), e);
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, AuthErrorCode.EXPIRED_TOKEN.getMessage());
            } catch (UnsupportedJwtException e) {
                log.error(AuthErrorCode.UNSUPPORTED_TOKEN.getMessage(), e);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, AuthErrorCode.UNSUPPORTED_TOKEN.getMessage());
            } catch (IllegalArgumentException e) {
                log.error(AuthErrorCode.EMPTY_CLAIMS.getMessage(), e);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, AuthErrorCode.EMPTY_CLAIMS.getMessage());
            } catch (Exception e) {
                log.error(AuthErrorCode.TOKEN_VERIFICATION_ERROR.getMessage(), e);
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        AuthErrorCode.TOKEN_VERIFICATION_ERROR.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
