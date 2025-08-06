package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistMovieException extends AlreadyExistEntityException {

    public AlreadyExistMovieException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
