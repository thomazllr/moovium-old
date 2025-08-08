package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistEntityException extends ResponseStatusException {

    public AlreadyExistEntityException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
