package com.thomazllr.moovium.response.seatReservation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatReservationGetResponse {

    private Long id;
    private String seatNumber;
    private String row;
    private String status;
    private String sessionTime;
    private String theaterName;
    private String movieTitle;
}
