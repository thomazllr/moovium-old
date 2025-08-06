package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistEntityException.class)
    public ResponseEntity<Problem> handleAlreadyExistEntityException(AlreadyExistEntityException exception) {

        var badRequest = HttpStatus.BAD_REQUEST;

        var problem = Problem.builder()
                .status(badRequest.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(badRequest).body(problem);
    }

    @ExceptionHandler(InvalidTheaterCapacityException.class)
    public ResponseEntity<Problem> handleInvalidTheaterCapacityException(InvalidTheaterCapacityException exception) {

        var badRequest = HttpStatus.BAD_REQUEST;

        var problem = Problem.builder()
                .status(badRequest.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(badRequest).body(problem);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException exception) {

        var notFound = HttpStatus.NOT_FOUND;

        var problem = Problem.builder()
                .status(notFound.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(notFound).body(problem);
    }
}
