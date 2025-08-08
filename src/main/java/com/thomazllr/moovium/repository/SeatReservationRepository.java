package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    Optional<SeatReservation> findBySeatIdAndSessionId(Long seatId, UUID sessionId);

    List<SeatReservation> findBySessionId(@Param("sessionId") UUID sessionId);
}
