package com.example.ondosocial.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DELETED_USER("탈퇴한 사용자 입니다."),
    ALREADY_SIGNED_UP_USER("이미 가입한 사용자 입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD_FORMAT("대소문자, 숫자, 특수문자를 포함한 8자 이상의 비밀번호를 입력해주세요."),
    SAME_PASSWORD_NOT_ALLOWED("동일한 비밀번호로 변경할 수 없습니다."),
    POST_NOT_FOUND("게시물을 찾을 수 없습니다."),
    NO_PERMISSION_TO_POST("게시물에 대한 권한이 없습니다."),
    FOLLOW_SELF_NOT_ALLOWED("본인 계정은 팔로우할 수 없습니다."),
    FOLLOWER_ALREADY_EXISTS("이미 팔로우 하고 있는 사용자 입니다."),
    FOLLOW_NOT_FOUND("팔로우하는 사용자가 아닙니다.");

    private final String message;
}
