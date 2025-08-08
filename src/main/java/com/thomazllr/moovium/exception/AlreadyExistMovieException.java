package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistMovieException extends AlreadyExistEntityException {

    public AlreadyExistMovieException(String name) {
        super("A movie with this name: '%s' already exists".formatted(name));
    }
}
