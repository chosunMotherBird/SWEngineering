package com.swteam6.EVCharger.domain.user.exception;

import lombok.Getter;

@Getter
public class EmailDuplicationException extends RuntimeException {
    private String email;

    public EmailDuplicationException(String email) {
        this.email = email;
    }
}
