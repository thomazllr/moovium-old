package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.SessionMapper;
import com.thomazllr.moovium.mapper.SessionSeatsMapper;
import com.thomazllr.moovium.request.session.SessionPostRequest;
import com.thomazllr.moovium.response.session.SessionGetResponse;
import com.thomazllr.moovium.response.session.SessionPostResponse;
import com.thomazllr.moovium.response.session.SessionSeatsStatusGetResponse;
import com.thomazllr.moovium.service.SessionService;
import com.thomazllr.moovium.service.query.SeatAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;
    private final SessionMapper mapper;

    private final SeatAvailabilityService seatAvailabilityService;
    private final SessionSeatsMapper sessionSeatsMapper;

    @PostMapping
    public ResponseEntity<SessionPostResponse> save(@RequestBody SessionPostRequest request) {
        var session = mapper.toEntity(request);
        var savedSession = service.save(session);
        var response = mapper.toSessionPostResponse(savedSession);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SessionGetResponse>> get() {
        var sessions = service.findAll();
        var response = mapper.toSessionGetResponseList(sessions);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionGetResponse> getById(@PathVariable String id) {
        var session = service.findByIdOrThrow(id);
        var response = mapper.toSessionGetResponse(session);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<SessionSeatsStatusGetResponse> getSessionSeatsStatusById(@PathVariable String id) {
        var seatsAvailabilityBySessionId = seatAvailabilityService.findSeatsWithStatusBySessionIdAsTuple(UUID.fromString(id));
        var response = sessionSeatsMapper.toResponse(UUID.fromString(id), seatsAvailabilityBySessionId);
        return ResponseEntity.ok(response);

    }


}
