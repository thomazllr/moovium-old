package com.thomazllr.moovium.response.theater;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TheaterSeatsGetResponse {

    public record Seat(Long id, String seatNumber, String row) {}

    private Long id;
    private String name;
    private Integer capacity;
    private String roomType;
    private List<Seat> seats;
}
