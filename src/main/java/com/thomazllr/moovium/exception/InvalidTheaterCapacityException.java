package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTheaterCapacityException extends ResponseStatusException {

    public InvalidTheaterCapacityException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
