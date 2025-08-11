package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.SeatReservationMapper;
import com.thomazllr.moovium.request.seatReservation.SeatReservationPostRequest;
import com.thomazllr.moovium.response.seatReservation.SeatReservationGetResponse;
import com.thomazllr.moovium.response.seatReservation.SeatReservationPostResponse;
import com.thomazllr.moovium.service.SeatReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/seat-reservations")
@RequiredArgsConstructor
@Tag(name = "Seat Reservations", description = "Operations about seat reservations for movie sessions")
@Slf4j
public class SeatReservationController {

    private final SeatReservationService service;
    private final SeatReservationMapper mapper;

    @PostMapping
    @Operation(
            summary = "Create a seat reservation",
            description = "Reserve one or more seats for a specific movie session"
    )
    @ApiResponse(responseCode = "200", description = "Seat reservation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "401", description = "Authentication required")
    @ApiResponse(responseCode = "403", description = "Access denied - User role required")
    @ApiResponse(responseCode = "404", description = "Session or seat not found")
    @ApiResponse(responseCode = "409", description = "Seat already reserved or session full")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SeatReservationPostResponse> save(@RequestBody @Valid SeatReservationPostRequest request) {

        log.info("Received seat reservation with SeatID: {} and SessionID: {}", request.getSeatId(), request.getSessionId());

        var seatReservation = mapper.toEntity(request);
        var savedSeatReservation = service.save(seatReservation);
        var response = mapper.toSeatReservationPostResponse(savedSeatReservation);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all seat reservations",
            description = "Retrieve a list of all seat reservations in the system"
    )
    @ApiResponse(responseCode = "200", description = "Seat reservations retrieved successfully")
    public ResponseEntity<List<SeatReservationGetResponse>> get() {
        var reservations = service.findAll();
        var response = mapper.toSeatReservationGetResponseList(reservations);
        return ResponseEntity.ok(response);
    }
}