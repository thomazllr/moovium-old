package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.AlreadyExistMovieException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Movie save(Movie movie) {
        assertThatMovieDoesNotExist(movie.getTitle());
        return repository.save(movie);
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public Movie findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: %s".formatted(id)));
    }

    public void delete(Long id) {
        var movie = findById(id);
        repository.delete(movie);
    }

    public void update(Movie movie) {
        repository.findByTitleIgnoreCase(movie.getTitle())
                .filter(found -> !found.getId().equals(movie.getId()))
                .ifPresent(duplicate -> {
                    throw new AlreadyExistMovieException("Another movie with title '%s' already exists.".formatted(movie.getTitle()));
                });

        repository.save(movie);
    }


    private void assertThatMovieDoesNotExist(String title) {
        repository.findByTitleIgnoreCase(title)
                .ifPresent(movie -> {
                    throw new AlreadyExistMovieException("A movie with this title: '%s' already exists".formatted(title));
                });
    }

}
