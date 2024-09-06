package com.example.ondosocial.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {
    INVALID_SIGNATURE("유효하지 않는 JWT 서명입니다."),
    EXPIRED_TOKEN("만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN("지원되지 않는 JWT 토큰입니다."),
    EMPTY_CLAIMS("잘못된 JWT 토큰입니다."),
    TOKEN_VERIFICATION_ERROR("JWT 토큰 검증 중 오류가 발생했습니다."),
    NOT_FOUND_TOKEN("JWT 토큰을 찾을 수 없습니다."),
    AUTH_TYPE_ERROR("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");

    private final String message;
}
