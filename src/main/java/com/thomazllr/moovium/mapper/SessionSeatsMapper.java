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

    private static final int ID_INDEX = 0;
    private static final int SEAT_NUMBER_INDEX = 1;
    private static final int ROW_INDEX = 2;
    private static final int STATUS_INDEX = 6;
    private static final int MOVIE_TITLE_INDEX = 7;
    private static final int SESSION_TIME_INDEX = 8;

    public SessionSeatsStatusGetResponse toResponse(UUID sessionId, List<Tuple> tuples) {
        if (tuples.isEmpty()) {
            return SessionSeatsStatusGetResponse.builder()
                    .sessionId(sessionId)
                    .seats(List.of())
                    .build();
        }

        Tuple firstTuple = tuples.get(0);
        String movieTitle = firstTuple.get(MOVIE_TITLE_INDEX, String.class);

        Timestamp timestamp = firstTuple.get(SESSION_TIME_INDEX, Timestamp.class);
        LocalDateTime sessionTime = timestamp.toLocalDateTime();

        List<SessionSeatsStatusGetResponse.SeatInfo> seats = tuples.stream()
                .map(this::tupleToSeatInfo)
                .toList();

        return SessionSeatsStatusGetResponse.builder()
                .sessionId(sessionId)
                .movieTitle(movieTitle)
                .sessionTime(sessionTime)
                .seats(seats)
                .build();
    }

    private SessionSeatsStatusGetResponse.SeatInfo tupleToSeatInfo(Tuple tuple) {
        return SessionSeatsStatusGetResponse.SeatInfo.builder()
                .id(tuple.get(ID_INDEX, Number.class).longValue())
                .seatNumber(tuple.get(SEAT_NUMBER_INDEX, String.class))
                .row(tuple.get(ROW_INDEX, String.class))
                .status(tuple.get(STATUS_INDEX, String.class))
                .build();
    }
}