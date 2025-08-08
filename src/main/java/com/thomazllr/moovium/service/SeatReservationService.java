package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.AlreadyExistSeatReservationException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.SeatReservation;
import com.thomazllr.moovium.model.Status;
import com.thomazllr.moovium.repository.SeatRepository;
import com.thomazllr.moovium.repository.SeatReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatReservationService {

    private final SeatReservationRepository repository;
    private final SeatRepository seatRepository;
    private final SessionService sessionService;


    public SeatReservation save(SeatReservation seatReservation) {

        String sessionId = seatReservation.getSession().getId().toString();
        Long seatId = seatReservation.getSeat().getId();

        var session = sessionService.findByIdOrThrow(sessionId);
        var seat = seatRepository.findById(seatId).orElseThrow(() -> new NotFoundException("Seat with id: '%d' not found".formatted(seatId)));

        assertThatSeatReservationDoesNotExist(seat.getId(), session.getId());

        seatReservation.setSession(session);
        seatReservation.setSeat(seat);
        seatReservation.setStatus(Status.RESERVED);
        seatReservation.setReservationExpiration(LocalDateTime.now().plusMinutes(10));

        return repository.save(seatReservation);
    }

    public List<SeatReservation> findAll() {
        return repository.findAll();
    }

    private void assertThatSeatReservationDoesNotExist(Long seatId, UUID sessionId) {
        repository.findBySeatIdAndSessionId(seatId, sessionId)
                .ifPresent(seatReservation -> {
                    throw new AlreadyExistSeatReservationException();
                });
    }
}
