package com.sklookiesmu.wisefee.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthForbbidenException extends RuntimeException {
    public AuthForbbidenException(String message) {
        super(message);
    }
}
