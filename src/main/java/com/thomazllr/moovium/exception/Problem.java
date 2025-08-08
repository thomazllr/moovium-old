package com.thomazllr.moovium.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class Problem {

    @Builder
    public record Field(String field, String message){}

    private int status;
    private String message;
    private String userMessage;
    private LocalDateTime timestamp;
    private List<Field> fields;
}
