package com.thomazllr.moovium.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class Problem {

    @Builder
    public record Field(String field, String message){}

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<Field> fields;
}
