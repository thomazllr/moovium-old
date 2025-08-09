package com.thomazllr.moovium.service.query;

import jakarta.persistence.Tuple;

import java.util.List;
import java.util.UUID;

public interface SeatAvailabilityService {

    List<Tuple> findSeatsWithStatusBySessionIdAsTuple(UUID sessionId);

}