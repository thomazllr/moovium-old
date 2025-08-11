package com.thomazllr.moovium.request.session;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionPostRequest {

    @NotNull(message = "Movie ID is required.")
    private Integer movieId;
    @NotNull(message = "Theater ID is required.")
    private Integer theaterId;
    @NotNull(message = "Session time is required.")
    private LocalDateTime sessionTime;
}
