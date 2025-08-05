package com.thomazllr.moovium.request.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MoviePostRequest {

    private String title;
    private String synopsis;
    private Integer duration;
    private LocalDate releaseDate;
    private String ageRating;
    private String posterUrl;
    private String status;
    private Boolean featured;
    private String featuredUntil;
}
