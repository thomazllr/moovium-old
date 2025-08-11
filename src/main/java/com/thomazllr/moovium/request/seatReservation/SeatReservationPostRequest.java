package com.thomazllr.moovium.request.seatReservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SeatReservationPostRequest {

    @NotNull(message = "Session ID is required.")
    private UUID sessionId;
    @NotBlank(message = "Seat ID is required.")
    private Long seatId;

}
