package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.AlreadyExistTheaterException;
import com.thomazllr.moovium.exception.InvalidTheaterCapacityException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Seat;
import com.thomazllr.moovium.model.Theater;
import com.thomazllr.moovium.repository.SeatRepository;
import com.thomazllr.moovium.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository repository;
    private final SeatRepository seatRepository;

    public Theater save(Theater theater) {
        validate(theater);
        var theaterSaved = repository.save(theater);
        generateSeats(theaterSaved);
        return theaterSaved;
    }

    public List<Theater> findAll() {
        return repository.findAll();
    }

    public Theater findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Theater not found with id: %s".formatted(id)));
    }

    public void delete(Long id) {
        var theater = findByIdOrThrow(id);
        repository.delete(theater);
    }


    public void validate(Theater theater) {
        assertThatTheaterDoesNotExist(theater.getName());
        validateTheaterCapacity(theater);
    }

    private void validateTheaterCapacity(Theater theater) {
        int capacity = theater.getCapacity();

        if (capacity < 100) {
            throw new InvalidTheaterCapacityException(
                    "Invalid capacity: %d. The minimum allowed is 100 seats.".formatted(capacity)
            );
        }

        if (capacity % 10 != 0) {
            throw new InvalidTheaterCapacityException(
                    "Invalid capacity: %d. Capacity must be a multiple of 10 (e.g., 100, 110, 120).".formatted(capacity)
            );
        }
    }


    private void assertThatTheaterDoesNotExist(String name) {
        repository.findByNameIgnoreCase(name)
                .ifPresent(theater -> {
                    throw new AlreadyExistTheaterException("A theater with this name: '%s' already exists".formatted(name));
                });
    }

    private void generateSeats(Theater theater) {
        int capacity = theater.getCapacity();
        int seatsPerRow = 10;
        int totalRows = (int) Math.ceil((double) capacity / seatsPerRow);

        List<Seat> seats = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            char rowLetter = (char) ('A' + rowIndex);

            for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                int seatCount = rowIndex * seatsPerRow + seatNumber;
                if (seatCount > capacity) break;

                String seatNum = String.format("%02d", seatNumber);

                Seat seat = Seat.builder()
                        .theater(theater)
                        .row(String.valueOf(rowLetter))
                        .seatNumber(seatNum)
                        .build();

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }

}
