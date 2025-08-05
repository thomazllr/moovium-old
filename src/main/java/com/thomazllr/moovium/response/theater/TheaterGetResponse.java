package com.thomazllr.moovium.response.theater;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheaterGetResponse {

    private Long id;
    private String name;
    private Integer capacity;
    private String roomType;
}
