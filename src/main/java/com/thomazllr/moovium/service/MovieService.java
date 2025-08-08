package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.AlreadyExistMovieException;
import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.thomazllr.moovium.repository.specs.MovieSpecs.isFeatured;
import static com.thomazllr.moovium.repository.specs.MovieSpecs.titleContains;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Movie save(Movie movie) {
        assertThatMovieDoesNotExist(movie.getTitle());
        return repository.save(movie);
    }

    public List<Movie> findAll(String title, Boolean featured) {

        Specification<Movie> specs = Specification.unrestricted();

        if (title != null) {
            specs = specs.and(titleContains(title));
        }

        specs = specs.and(isFeatured(featured));

        return repository.findAll(specs);

    }

    public Movie findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: %s".formatted(id)));
    }

    public Movie findByIdOrBadRequest(Long movieId) {
        try {
            return findByIdOrThrow(movieId);
        } catch (NotFoundException e) {
            throw new BusinessException("Invalid Movie Id: '%d'".formatted(movieId));
        }
    }

    public Movie findByTitle(String title) {
        return repository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new NotFoundException("Movie not found with title: %s".formatted(title)));
    }

    public void delete(Long id) {
        var movie = findByIdOrThrow(id);
        repository.delete(movie);
    }

    public void update(Movie movie) {
        repository.findByTitleIgnoreCase(movie.getTitle())
                .filter(found -> !found.getId().equals(movie.getId()))
                .ifPresent(duplicate -> {
                    throw new AlreadyExistMovieException(movie.getTitle());
                });

        repository.save(movie);
    }


    private void assertThatMovieDoesNotExist(String title) {
        repository.findByTitleIgnoreCase(title)
                .ifPresent(movie -> {
                    throw new AlreadyExistMovieException(title);
                });
    }

}
