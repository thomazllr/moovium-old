package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Session;
import com.thomazllr.moovium.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final MovieService movieService;
    private final TheaterService theaterService;

    public Session save(Session session) {
        var movie = movieService.findByIdOrThrow(session.getMovie().getId());
        var theater = theaterService.findByIdOrThrow(session.getTheater().getId());
        session.setMovie(movie);
        session.setTheater(theater);
        return repository.save(session);
    }

    public Session findByIdOrThrow(String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Session not found with id: %s".formatted(id)));
    }

    public List<Session> findAll() {
        return repository.findAll();
    }
}
