package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.TheaterMapper;
import com.thomazllr.moovium.request.theater.TheaterPostRequest;
import com.thomazllr.moovium.response.theater.TheaterGetResponse;
import com.thomazllr.moovium.response.theater.TheaterPostResponse;
import com.thomazllr.moovium.response.theater.TheaterSeatsGetResponse;
import com.thomazllr.moovium.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/theaters")
public class TheaterController {

    private final TheaterService service;
    private final TheaterMapper mapper;

    @PostMapping
    public ResponseEntity<TheaterPostResponse> save(@RequestBody TheaterPostRequest request) {
        var theater = mapper.toTheater(request);
        var savedTheater = service.save(theater);
        var response = mapper.toTheaterPostResponse(savedTheater);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<TheaterGetResponse>> get() {
        var list = service.findAll();
        var response = mapper.toTheaterGetResponseList(list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterGetResponse> getById(@PathVariable Long id) {
        var theater = service.findByIdOrThrow(id);
        var response = mapper.toTheaterGetResponse(theater);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<TheaterSeatsGetResponse> getTheaterSeatsById(@PathVariable Long id) {
        var theater = service.findByIdOrThrow(id);
        var response = mapper.toTheaterSeatsGetResponse(theater);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
