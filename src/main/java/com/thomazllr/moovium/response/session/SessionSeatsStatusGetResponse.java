package com.thomazllr.moovium.response.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SessionSeatsStatusGetResponse {

    @Builder
    public record SeatInfo(Long id, String seatNumber, String row, String status) {}

    private String movieTitle;
    private LocalDateTime sessionTime;
    private UUID sessionId;
    private List<SeatInfo> seats;
}
