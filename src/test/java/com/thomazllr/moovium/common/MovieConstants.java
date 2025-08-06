package com.thomazllr.moovium.common;

import com.thomazllr.moovium.model.Genre;
import com.thomazllr.moovium.model.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovieConstants {

    public static final Movie MOVIE = Movie.builder()
            .title("The Matrix")
            .synopsis("A hacker discovers the reality is a simulation.")
            .genre(Genre.ACTION)
            .duration(136)
            .releaseDate(LocalDate.of(1999, 3, 31))
            .ageRating("R")
            .posterUrl("https://image.tmdb.org/t/p/w500/matrix.jpg")
            .status("RELEASED")
            .featured(true)
            .featuredUntil(LocalDate.now().plusDays(30))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    public static final Movie INVALID_MOVIE = Movie.builder()
            .title(" ")
            .synopsis("")
            .genre(null)
            .duration(null)
            .releaseDate(null)
            .ageRating("")
            .posterUrl(" ")
            .status(" ")
            .featured(null)
            .featuredUntil(null)
            .createdAt(null)
            .updatedAt(null)
            .build();
}
