package com.swteam6.EVCharger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Song Hak Hyeon
 * 존재하지 않는 회원을 찾는 예외 처리를 위한 클래스 구현
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
