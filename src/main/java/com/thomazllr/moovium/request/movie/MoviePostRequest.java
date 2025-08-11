package com.thomazllr.moovium.request.movie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MoviePostRequest {

    @NotBlank(message = "Title is required")
    private String title;
    private String synopsis;

    @NotNull(message = "Duration is required.")
    @Min(value = 1, message = "Duration must be at least 1 minute.")
    private Integer duration;

    @NotNull(message = "Release date is required.")
    private LocalDate releaseDate;

    @NotBlank(message = "Age rating is required.")
    private String ageRating;

    @NotBlank(message = "Poster URL is required.")
    private String posterUrl;

    @NotBlank(message = "Status is required.")
    private String status;

    @NotNull(message = "Featured flag must be provided.")
    private Boolean featured;

    @NotBlank(message = "Featured until is required.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Featured until must be in format yyyy-MM-dd.")
    private String featuredUntil;
}
