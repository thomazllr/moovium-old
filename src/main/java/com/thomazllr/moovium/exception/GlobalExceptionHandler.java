package com.thomazllr.moovium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistMovieException.class)
    public ResponseEntity<Problem> handleAlreadyExistMovieException(AlreadyExistMovieException exception) {

        var badRequest = HttpStatus.BAD_REQUEST;

        var problem =  Problem.builder()
                .status(badRequest.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(problem);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException exception) {

        var badRequest = HttpStatus.NOT_FOUND;

        var problem =  Problem.builder()
                .status(badRequest.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(problem);
    }
}
