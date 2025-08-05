package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistTheaterException extends AlreadyExistEntityException {

    public AlreadyExistTheaterException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
