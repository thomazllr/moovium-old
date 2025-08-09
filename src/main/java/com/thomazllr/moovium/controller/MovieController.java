package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.MovieMapper;
import com.thomazllr.moovium.request.movie.MoviePostRequest;
import com.thomazllr.moovium.request.movie.MoviePutRequest;
import com.thomazllr.moovium.response.movie.MovieGetResponse;
import com.thomazllr.moovium.response.movie.MoviePostResponse;
import com.thomazllr.moovium.response.movie.MovieSessionsGetResponse;
import com.thomazllr.moovium.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/movies")
public class MovieController {

    private final MovieService service;
    private final MovieMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MoviePostResponse> save(@RequestBody @Valid MoviePostRequest request) {
        var movie = mapper.toEntity(request);
        var savedMovie = service.save(movie);
        var response = mapper.toMoviePostResponse(savedMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<MovieGetResponse>> get(@RequestParam(required = false) String title,
                                                      @RequestParam(required = false, defaultValue = "true") Boolean featured) {

        var list = service.findAll(title, featured);
        var response = mapper.toMovieGetResponseList(list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<MovieGetResponse> getByTitle(@PathVariable String title) {
        var movie = service.findByTitle(title);
        var response = mapper.toMovieGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieGetResponse> getById(@PathVariable Long id) {
        var movie = service.findByIdOrThrow(id);
        var response = mapper.toMovieGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<MovieSessionsGetResponse> getSessionsByMovieId(@PathVariable Long id) {
        var movie = service.findByIdOrThrow(id);
        var response = mapper.toMovieSessionsGetResponse(movie);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody MoviePutRequest request) {
        var movie = service.findByIdOrThrow(request.getId());
        mapper.updateMovieFromPutRequest(request, movie);
        service.update(movie);
        return ResponseEntity.noContent().build();
    }
}
