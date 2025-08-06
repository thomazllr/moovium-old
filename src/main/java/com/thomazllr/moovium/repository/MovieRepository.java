package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    Optional<Movie> findByTitleIgnoreCase(String title);

}
