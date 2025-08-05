package com.thomazllr.moovium.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Problem {

    private int status;
    private String message;

    private LocalDateTime timestamp;
}
