package com.thomazllr.moovium.request.seatReservation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SeatReservationPostRequest {

    private UUID sessionId;
    private Long seatId;

}
