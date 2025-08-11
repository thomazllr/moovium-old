package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.TheaterMapper;
import com.thomazllr.moovium.request.theater.TheaterPostRequest;
import com.thomazllr.moovium.response.theater.TheaterGetResponse;
import com.thomazllr.moovium.response.theater.TheaterPostResponse;
import com.thomazllr.moovium.response.theater.TheaterSeatsGetResponse;
import com.thomazllr.moovium.service.TheaterService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/theaters")
@Tag(name = "Theaters", description = "Operations about movie theaters")
public class TheaterController {

    private final TheaterService service;
    private final TheaterMapper mapper;

    @PostMapping
    @Operation(
            summary = "Create a new theater",
            description = "Create a new movie theater with the given information"
    )
    @ApiResponse(responseCode = "201", description = "Theater created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Theater already exists")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheaterPostResponse> save(@RequestBody @Valid TheaterPostRequest request) {
        var theater = mapper.toEntity(request);
        var savedTheater = service.save(theater);
        var response = mapper.toTheaterPostResponse(savedTheater);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all theaters",
            description = "Retrieve a list of all movie theaters"
    )
    @ApiResponse(responseCode = "200", description = "Theaters retrieved successfully")
    public ResponseEntity<List<TheaterGetResponse>> get() {
        var list = service.findAll();
        var response = mapper.toTheaterGetResponseList(list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get theater by ID",
            description = "Retrieve a specific theater by its unique identifier"
    )
    @ApiResponse(responseCode = "200", description = "Theater found successfully")
    @ApiResponse(responseCode = "404", description = "Theater not found")
    public ResponseEntity<TheaterGetResponse> getById(@PathVariable Long id) {
        var theater = service.findByIdOrThrow(id);
        var response = mapper.toTheaterGetResponse(theater);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seats")
    @Operation(
            summary = "Get theater seats",
            description = "Retrieve all seats configuration for a specific theater"
    )
    @ApiResponse(responseCode = "200", description = "Theater seats retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Theater not found")
    public ResponseEntity<TheaterSeatsGetResponse> getTheaterSeatsById(@PathVariable Long id) {
        var theater = service.findByIdOrThrow(id);
        var response = mapper.toTheaterSeatsGetResponse(theater);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a theater",
            description = "Remove a theater from the system by its ID"
    )
    @ApiResponse(responseCode = "204", description = "Theater deleted successfully")
    @ApiResponse(responseCode = "404", description = "Theater not found")
    @ApiResponse(responseCode = "409", description = "Cannot delete theater - has active sessions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}