package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistEntityException.class)
    public ResponseEntity<Problem> handleAlreadyExistEntityException(AlreadyExistEntityException exception) {

        var conflict = HttpStatus.CONFLICT;

        var problem = Problem.builder()
                .status(conflict.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(conflict).body(problem);
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        var badRequest = HttpStatus.BAD_REQUEST;

        List<Problem.Field> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> Problem.Field.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        var problem = Problem.builder()
                .status(badRequest.value())
                .message("Validation failed for one or more fields.")
                .fields(fieldErrors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(badRequest).body(problem);

    }
}
