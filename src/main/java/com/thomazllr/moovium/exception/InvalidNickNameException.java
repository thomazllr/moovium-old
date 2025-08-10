package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidNickNameException extends ResponseStatusException {

    public InvalidNickNameException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
