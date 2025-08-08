package com.thomazllr.moovium.exception;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BusinessException extends ResponseStatusException {

    public BusinessException() {
        super(BAD_REQUEST);
    }

    public BusinessException(String reason) {
        super(BAD_REQUEST, reason);
    }
}
