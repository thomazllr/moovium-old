package com.thomazllr.moovium.exception;

public class AlreadyExistTheaterException extends AlreadyExistEntityException {

    public AlreadyExistTheaterException(String name) {
        super("A theater with this name: '%s' already exists".formatted(name));
    }
}
