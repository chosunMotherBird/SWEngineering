package com.swteam6.EVCharger.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Song Hak Hyeon
 * email을 기준으로 중복되는 회원을 등록시 발생하는 예외 처리용 클래스 구현
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailDuplicationException extends RuntimeException {
    private String email;

    public EmailDuplicationException(String email) {
        this.email = email;
    }
}
