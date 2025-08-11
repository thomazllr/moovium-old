package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.MovieMapper;
import com.thomazllr.moovium.request.movie.MoviePostRequest;
import com.thomazllr.moovium.request.movie.MoviePutRequest;
import com.thomazllr.moovium.response.movie.MovieGetResponse;
import com.thomazllr.moovium.response.movie.MoviePostResponse;
import com.thomazllr.moovium.response.movie.MovieSessionsGetResponse;
import com.thomazllr.moovium.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/movies")
@Tag(name = "Movies", description = "Operations about movies")
@Slf4j
public class MovieController {

    private final MovieService service;
    private final MovieMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new movie",
            description = "Create a new movie with the given information"
    )
    @ApiResponse(responseCode = "201", description = "Movie created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Movie already exists")
    public ResponseEntity<MoviePostResponse> save(@RequestBody @Valid MoviePostRequest request) {

        log.info("Saving movie: {}", request.getTitle());

        var movie = mapper.toEntity(request);
        var savedMovie = service.save(movie);
        var response = mapper.toMoviePostResponse(savedMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all movies",
            description = "Retrieve a list of movies with optional filters for title and featured status"
    )
    @ApiResponse(responseCode = "200", description = "Movies retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid query parameters")
    public ResponseEntity<List<MovieGetResponse>> get(@RequestParam(required = false) String title,
                                                      @RequestParam(required = false, defaultValue = "true") Boolean featured) {

        var list = service.findAll(title, featured);
        var response = mapper.toMovieGetResponseList(list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/title/{title}")
    @Operation(
            summary = "Get movie by title",
            description = "Retrieve a specific movie by its title"
    )
    @ApiResponse(responseCode = "200", description = "Movie found successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<MovieGetResponse> getByTitle(@PathVariable String title) {
        var movie = service.findByTitle(title);
        var response = mapper.toMovieGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get movie by ID",
            description = "Retrieve a specific movie by its unique identifier"
    )
    @ApiResponse(responseCode = "200", description = "Movie found successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<MovieGetResponse> getById(@PathVariable Long id) {
        var movie = service.findByIdOrThrow(id);
        var response = mapper.toMovieGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/sessions")
    @Operation(
            summary = "Get movie sessions",
            description = "Retrieve all sessions available for a specific movie"
    )
    @ApiResponse(responseCode = "200", description = "Movie sessions retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<MovieSessionsGetResponse> getSessionsByMovieId(@PathVariable Long id) {
        var movie = service.findByIdOrThrow(id);
        var response = mapper.toMovieSessionsGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a movie",
            description = "Remove a movie from the system by its ID"
    )
    @ApiResponse(responseCode = "204", description = "Movie deleted successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting movie with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(
            summary = "Update a movie",
            description = "Update an existing movie with new information"
    )
    @ApiResponse(responseCode = "204", description = "Movie updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<Void> update(@RequestBody @Valid MoviePutRequest request) {

        log.info("Updating movie with ID: {}", request.getId());

        var movie = service.findByIdOrThrow(request.getId());
        mapper.updateMovieFromPutRequest(request, movie);
        service.update(movie);
        return ResponseEntity.noContent().build();
    }
}