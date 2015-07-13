package com.lge.cto.swinfra.tiger.exception;

/**
 * 유효하지 않는 Date 형식의 문자열
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class InvalidDateString extends RuntimeException {

    public InvalidDateString(String message) {
        super(message);
    }

    public InvalidDateString(String message, Throwable cause) {
        super(message, cause);
    }
}
