package com.thomazllr.moovium.request.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MoviePutRequest {

    private Long id;
    private String title;
}
