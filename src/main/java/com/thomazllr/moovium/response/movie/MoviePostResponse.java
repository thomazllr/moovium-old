package com.thomazllr.moovium.response.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MoviePostResponse {

    private Long id;
    private String title;
    private Boolean featured;

}
