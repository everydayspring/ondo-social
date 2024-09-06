package com.example.ondosocial.config.validate;

import com.example.ondosocial.config.error.ErrorCode;

public class Preconditions {

    public static void validate(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new IllegalArgumentException(errorCode.getMessage());
        }
    }
}
