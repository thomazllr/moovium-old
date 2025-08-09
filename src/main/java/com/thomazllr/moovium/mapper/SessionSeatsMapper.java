package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.response.session.SessionSeatsStatusGetResponse;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class SessionSeatsMapper {

    public SessionSeatsStatusGetResponse toResponse(UUID sessionId, List<Tuple> tuples) {
        if (tuples == null || tuples.isEmpty()) {
            return SessionSeatsStatusGetResponse.builder()
                    .sessionId(sessionId)
                    .seats(List.of())
                    .build();
        }

        Tuple first = tuples.getFirst();

        String movieTitle = first.get("movie_title", String.class);
        LocalDateTime sessionTime = toLocalDateTime(first.get("session_time", Timestamp.class));

        List<SessionSeatsStatusGetResponse.SeatInfo> seats = tuples.stream()
                .map(this::toSeatInfo)
                .toList();

        return SessionSeatsStatusGetResponse.builder()
                .sessionId(sessionId)
                .movieTitle(movieTitle)
                .sessionTime(sessionTime)
                .seats(seats)
                .build();
    }

    private SessionSeatsStatusGetResponse.SeatInfo toSeatInfo(Tuple tuple) {
        return SessionSeatsStatusGetResponse.SeatInfo.builder()
                .id(tuple.get("seat_id", Number.class).longValue())
                .seatNumber(tuple.get("seat_number", String.class))
                .row(tuple.get("seat_row", String.class))
                .status(tuple.get("seat_status", String.class))
                .build();
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
