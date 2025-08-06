package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.model.Genre;
import com.thomazllr.moovium.response.genre.GenreGetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1/genres")
public class GenreController {

    @GetMapping
    public ResponseEntity<List<GenreGetResponse>> getAllGenres() {
        List<GenreGetResponse> genres = Arrays.stream(Genre.values())
                .map(GenreGetResponse::toGenreGetResponse)
                .toList();

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<GenreGetResponse> getGenreByName(@PathVariable String genre) {
        var movieGenre = Genre.valueOf(genre);
        var genreGetResponse = GenreGetResponse.toGenreGetResponse(movieGenre);
        return ResponseEntity.ok(genreGetResponse);
    }
}
