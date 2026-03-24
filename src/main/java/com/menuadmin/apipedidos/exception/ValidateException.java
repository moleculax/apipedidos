package com.menuadmin.apipedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class ValidateException extends RuntimeException{
    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }
}
