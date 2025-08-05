package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistMovieException extends ResponseStatusException {

    public AlreadyExistMovieException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
