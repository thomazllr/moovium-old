package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.model.Genre;
import com.thomazllr.moovium.response.genre.GenreGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1/genres")
@Tag(name = "Genres", description = "Operations about movie genres")
public class GenreController {

    @GetMapping
    @Operation(
            summary = "Get all genres",
            description = "Retrieve a list of all available movie genres"
    )
    @ApiResponse(responseCode = "200", description = "Genres retrieved successfully")
    public ResponseEntity<List<GenreGetResponse>> getAllGenres() {
        List<GenreGetResponse> genres = Arrays.stream(Genre.values())
                .map(GenreGetResponse::toGenreGetResponse)
                .toList();

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genre}")
    @Operation(
            summary = "Get genre by name",
            description = "Retrieve a specific genre by its name"
    )
    @ApiResponse(responseCode = "200", description = "Genre found successfully")
    @ApiResponse(responseCode = "400", description = "Invalid genre name")
    @ApiResponse(responseCode = "404", description = "Genre not found")
    public ResponseEntity<GenreGetResponse> getGenreByName(@PathVariable String genre) {
        var movieGenre = Genre.valueOf(genre);
        var genreGetResponse = GenreGetResponse.toGenreGetResponse(movieGenre);
        return ResponseEntity.ok(genreGetResponse);
    }
}