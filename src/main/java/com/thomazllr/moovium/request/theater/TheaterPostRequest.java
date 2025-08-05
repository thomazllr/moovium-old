package com.thomazllr.moovium.request.theater;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheaterPostRequest {

    private String name;
    private Integer capacity;
    private String roomType;
}
