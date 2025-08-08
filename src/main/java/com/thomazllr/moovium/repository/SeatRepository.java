package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTheaterId(@Param("theaterId") Long theaterId);
}
