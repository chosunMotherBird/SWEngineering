package com.swteam6.EVCharger.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailDuplicationException extends RuntimeException {
    private String email;

    public EmailDuplicationException(String email) {
        this.email = email;
    }
}
