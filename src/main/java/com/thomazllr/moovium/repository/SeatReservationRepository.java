package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    Optional<SeatReservation> findBySeatIdAndSessionId(Long seatId, UUID sessionId);
}
