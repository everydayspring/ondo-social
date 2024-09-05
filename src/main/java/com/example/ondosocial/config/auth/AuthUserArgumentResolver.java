package com.example.ondosocial.config.auth;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.config.error.AuthErrorCode;
import com.example.ondosocial.domain.user.dto.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = Objects.nonNull(parameter.getParameterAnnotation(Auth.class));
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        // @Auth 어노테이션과 AuthUser 타입이 함께 사용되지 않은 경우 예외 발생
        if (Objects.equals(hasAuthAnnotation, isAuthUserType)) {
            throw new IllegalArgumentException(AuthErrorCode.AUTH_TYPE_ERROR.getMessage());
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // JwtFilter 에서 set 한 userId, email 값을 가져옴
        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");

        return new AuthUser(userId, email);
    }
}