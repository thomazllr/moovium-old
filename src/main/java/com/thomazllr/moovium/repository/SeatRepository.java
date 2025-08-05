package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
