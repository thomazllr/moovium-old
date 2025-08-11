package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.SessionMapper;
import com.thomazllr.moovium.mapper.SessionSeatsMapper;
import com.thomazllr.moovium.request.session.SessionPostRequest;
import com.thomazllr.moovium.response.session.SessionGetResponse;
import com.thomazllr.moovium.response.session.SessionPostResponse;
import com.thomazllr.moovium.response.session.SessionSeatsStatusGetResponse;
import com.thomazllr.moovium.service.SessionService;
import com.thomazllr.moovium.service.query.SeatAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Operations about movie sessions")
public class SessionController {

    private final SessionService service;
    private final SessionMapper mapper;

    private final SeatAvailabilityService seatAvailabilityService;
    private final SessionSeatsMapper sessionSeatsMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new session",
            description = "Create a new movie session with the given information"
    )
    @ApiResponse(responseCode = "201", description = "Session created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    @ApiResponse(responseCode = "409", description = "Session already exists or time conflict")
    public ResponseEntity<SessionPostResponse> save(@RequestBody @Valid SessionPostRequest request) {
        var session = mapper.toEntity(request);
        var savedSession = service.save(session);
        var response = mapper.toSessionPostResponse(savedSession);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all sessions",
            description = "Retrieve a list of all movie sessions"
    )
    @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully")
    public ResponseEntity<List<SessionGetResponse>> get() {
        var sessions = service.findAll();
        var response = mapper.toSessionGetResponseList(sessions);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get session by ID",
            description = "Retrieve a specific session by its unique identifier"
    )
    @ApiResponse(responseCode = "200", description = "Session found successfully")
    @ApiResponse(responseCode = "400", description = "Invalid UUID format")
    @ApiResponse(responseCode = "404", description = "Session not found")
    public ResponseEntity<SessionGetResponse> getById(@PathVariable String id) {
        var session = service.findByIdOrThrow(id);
        var response = mapper.toSessionGetResponse(session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seats")
    @Operation(
            summary = "Get session seats status",
            description = "Retrieve the availability status of all seats for a specific session"
    )
    @ApiResponse(responseCode = "200", description = "Session seats status retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid UUID format")
    @ApiResponse(responseCode = "404", description = "Session not found")
    public ResponseEntity<SessionSeatsStatusGetResponse> getSessionSeatsStatusById(@PathVariable String id) {
        var seatsAvailabilityBySessionId = seatAvailabilityService.findSeatsWithStatusBySessionIdAsTuple(UUID.fromString(id));
        var response = sessionSeatsMapper.toResponse(UUID.fromString(id), seatsAvailabilityBySessionId);
        return ResponseEntity.ok(response);
    }
}