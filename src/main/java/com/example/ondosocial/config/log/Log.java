package com.example.ondosocial.config.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {
    public static void success(String className) {
        log.info(
                className
                        + "."
                        + Thread.currentThread().getStackTrace()[2].getMethodName()
                        + "  ::: 성공 ::: ");
    }
}
