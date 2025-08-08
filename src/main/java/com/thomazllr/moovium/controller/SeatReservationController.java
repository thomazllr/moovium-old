package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.SeatReservationMapper;
import com.thomazllr.moovium.request.seatReservation.SeatReservationPostRequest;
import com.thomazllr.moovium.response.seatReservation.SeatReservationGetResponse;
import com.thomazllr.moovium.response.seatReservation.SeatReservationPostResponse;
import com.thomazllr.moovium.service.SeatReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/seat-reservations")
@RequiredArgsConstructor
public class SeatReservationController {

    private final SeatReservationService service;
    private final SeatReservationMapper mapper;

    @PostMapping
    public ResponseEntity<SeatReservationPostResponse> save(@RequestBody SeatReservationPostRequest request) {
        var seatReservation = mapper.toEntity(request);
        var savedSeatReservation = service.save(seatReservation);
        var response = mapper.toSeatReservationPostResponse(savedSeatReservation);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SeatReservationGetResponse>> get() {
        var reservations = service.findAll();
        var response = mapper.toSeatReservationGetResponseList(reservations);
        return ResponseEntity.ok(response);
    }

}
