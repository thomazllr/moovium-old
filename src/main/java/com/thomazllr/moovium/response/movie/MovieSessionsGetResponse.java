package com.thomazllr.moovium.response.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieSessionsGetResponse {

    public record Session(String theaterName, String sessionTime){}

    private Long id;
    private String title;
    private List<Session> sessions;

}
