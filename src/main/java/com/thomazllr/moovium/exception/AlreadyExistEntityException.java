package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistEntityException extends ResponseStatusException {

    public AlreadyExistEntityException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
