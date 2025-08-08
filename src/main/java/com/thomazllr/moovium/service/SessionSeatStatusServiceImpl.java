package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.repository.SessionRepository;
import com.thomazllr.moovium.service.query.SessionSeatStatusService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionSeatStatusServiceImpl implements SessionSeatStatusService {

    @PersistenceContext
    private EntityManager entityManager;

    private final SessionRepository repository;

    @SuppressWarnings("unchecked")
    public List<Tuple> findSeatsWithStatusBySessionIdAsTuple(UUID sessionId) {

        repository.findById(sessionId).orElseThrow(() -> new NotFoundException("Invalid Session Id: '%s'".formatted(sessionId)));

        String sql = """
                SELECT s.id, s.seat_number, s.row, s.theater_id, s.created_at, s.updated_at,
                       COALESCE(sr.status, 'AVAILABLE') as status,
                       m.title as movie_title,
                       ses.session_time
                FROM seat s
                LEFT JOIN seat_reservation sr ON s.id = sr.seat_id AND sr.session_id = :sessionId
                INNER JOIN session ses ON ses.theater_id = s.theater_id AND ses.id = :sessionId
                INNER JOIN movie m ON m.id = ses.movie_id
                WHERE s.theater_id = (SELECT theater_id FROM session WHERE id = :sessionId)
                ORDER BY s.row, s.seat_number
                """;

        return entityManager.createNativeQuery(sql, Tuple.class)
                .setParameter("sessionId", sessionId)
                .getResultList();
    }


}
