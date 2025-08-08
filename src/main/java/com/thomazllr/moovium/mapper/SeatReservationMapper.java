package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.Seat;
import com.thomazllr.moovium.model.SeatReservation;
import com.thomazllr.moovium.model.Session;
import com.thomazllr.moovium.request.seatReservation.SeatReservationPostRequest;
import com.thomazllr.moovium.response.seatReservation.SeatReservationGetResponse;
import com.thomazllr.moovium.response.seatReservation.SeatReservationPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SeatReservationMapper {


    @Mapping(target = "session", source = "sessionId")
    @Mapping(target = "seat", source = "seatId")
    SeatReservation toEntity(SeatReservationPostRequest request);

    SeatReservationPostResponse toSeatReservationPostResponse(SeatReservation seatReservation);

    @Mapping(target = "movieTitle", source = "session.movie.title")
    @Mapping(target = "theaterName", source = "seat.theater.name")
    @Mapping(target = "seatNumber", source = "seat.seatNumber")
    @Mapping(target = "row", source = "seat.row")
    @Mapping(target = "sessionTime", source = "session.sessionTime")
    SeatReservationGetResponse toSeatReservationGetResponse(SeatReservation seatReservation);

    List<SeatReservationGetResponse> toSeatReservationGetResponseList(List<SeatReservation> seatReservations);


    default Session mapSession(UUID sessionId) {
        if (sessionId == null) return null;
        Session session = new Session();
        session.setId(sessionId);
        return session;
    }

    default Seat mapSeat(Long seatId) {
        if (seatId == null) return null;
        Seat seat = new Seat();
        seat.setId(seatId);
        return seat;
    }

}
