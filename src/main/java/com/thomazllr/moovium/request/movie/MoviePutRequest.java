package com.thomazllr.moovium.request.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MoviePutRequest {

    @NotNull(message = "ID is required.")
    private Long id;
    @NotBlank(message = "Title is required.")
    private String title;
}
