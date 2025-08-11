package com.thomazllr.moovium.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "Internal Error - Please try again later or contact the support team.";

    @ExceptionHandler(AlreadyExistEntityException.class)
    public ResponseEntity<Problem> handleAlreadyExistEntityException(AlreadyExistEntityException exception) {

        log.error(exception.getMessage());

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

        log.error(exception.getMessage());

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

        log.error(exception.getMessage());

        var notFound = HttpStatus.NOT_FOUND;

        var problem = Problem.builder()
                .status(notFound.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(notFound).body(problem);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.error(ex.getMessage());


        var fields = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> Problem.Field.builder()
                        .field(err.getField())
                        .message(err.getDefaultMessage())
                        .build())
                .toList();

        var problem = Problem.builder()
                .status(status.value())
                .message("Validation failed for one or more fields.")
                .fields(fields)
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Problem> handleBusinessException(BusinessException exception) {

        log.error(exception.getMessage());


        var badRequest = HttpStatus.BAD_REQUEST;

        var problem = Problem.builder()
                .status(badRequest.value())
                .message(exception.getReason())
                .timestamp(LocalDateTime.now())
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Problem> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {

        log.error(exception.getMessage());

        var forbidden = HttpStatus.FORBIDDEN;
        var problem = Problem.builder()
                .status(forbidden.value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(forbidden).body(problem);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        log.error(exception.getMessage());

        var badRequest = HttpStatus.BAD_REQUEST;
        Throwable rootCause = ExceptionUtils.getRootCause(exception);

        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormatException(invalidFormatException, headers, badRequest, request);
        }

        var problem = Problem.builder()
                .status(badRequest.value())
                .message("Invalid request body.")
                .timestamp(LocalDateTime.now())
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(exception, problem, headers, badRequest, request);

    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        log.error(ex.getMessage());

        var path = ex.getPath().stream()
                .map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        String detail = String.format("Property '%s' has been assigned a value '%s' which is invalid. Correct and enter the value with the type %s.",
                path,
                ex.getValue(),
                ex.getTargetType().getSimpleName());

        Problem problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        String mensagem;
        if (status instanceof HttpStatus httpStatus) {
            mensagem = httpStatus.getReasonPhrase();
        } else {
            mensagem = status.toString();
        }

        if (body == null) {
            body = Problem.builder()
                    .message(mensagem)
                    .status(status.value())
                    .timestamp(LocalDateTime.now())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        } else if (body instanceof String corpo) {
            body = Problem.builder()
                    .message(corpo)
                    .timestamp(LocalDateTime.now())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
