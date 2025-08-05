package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleIgnoreCase(String title);
    Optional<Movie> findByTitleIgnoreCaseAndIdNot(String title, Long id);
}
