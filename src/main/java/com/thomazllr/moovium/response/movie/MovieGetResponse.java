package com.thomazllr.moovium.response.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieGetResponse {

    private Long id;
    private String title;
    private String synopsis;
    private Integer duration;
    private String releaseDate;
    private String ageRating;
    private String posterUrl;
    private String status;
    private boolean featured;
    private String featuredUntil;
}
