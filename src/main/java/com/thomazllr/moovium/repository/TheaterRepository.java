package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {


    Optional<Theater> findByNameIgnoreCase(String name);
}
