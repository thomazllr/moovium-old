package com.thomazllr.moovium.response.theater;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheaterPostResponse {

    private Long id;
    private String name;

}
