package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.thomazllr.moovium.common.MovieConstants.INVALID_MOVIE;
import static com.thomazllr.moovium.common.MovieConstants.MOVIE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    private List<Movie> movies = new ArrayList<>();

    @Test
    @DisplayName("Create a movie with valid data returns a movie")
    @Order(1)
    void createMovie_WithValidData_ReturnsAMovie() {

        when(repository.save(MOVIE)).thenReturn(MOVIE);

        Movie sut = service.save(MOVIE);

        assertThat(sut).isEqualTo(MOVIE);
    }

    @Test
    @DisplayName("Create a movie with invalid data throws Exception")
    @Order(2)
    void createMovie_WithInvalidData_ThrowsException() {

        when(repository.save(INVALID_MOVIE)).thenThrow(new RuntimeException("Invalid data"));

        assertThatThrownBy(() -> service.save(INVALID_MOVIE)).isInstanceOf(RuntimeException.class);


    }

    @Test
    @DisplayName("Get a movie by existing id returns a movie")
    @Order(3)
    void getMovie_ByExistingId_ReturnsAMovie() {

        var id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(MOVIE));

        Movie sut = service.findById(id);

        assertThat(sut).isEqualTo(MOVIE);
    }

    @Test
    @DisplayName("Get a movie by existing id throws NotFoundException")
    @Order(4)
    void getMovie_ByUnexistingId_ThrowsNotFoundException() {

        var id = 99L;

        when(repository.findById(id)).thenThrow(new NotFoundException("Movie not found with id: %d".formatted(id)));

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(NotFoundException.class);


    }

    @Test
    @DisplayName("Get a movie by existing title returns a movie")
    @Order(5)
    void getMovie_ByExistingTitle_ReturnsAMovie() {

        var name = "The Matrix";

        when(repository.findByTitleIgnoreCase(name)).thenReturn(Optional.of(MOVIE));

        Movie sut = service.findByTitle(name);

        assertThat(sut).isEqualTo(MOVIE);
    }

    @Test
    @DisplayName("Get a movie by existing title throws NotFoundException")
    @Order(6)
    void getMovie_ByUnexistingTitle_ThrowsNotFoundException() {

        var name = "not-found";

        when(repository.findByTitleIgnoreCase(name)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByTitle(name)).isInstanceOf(NotFoundException.class);


    }

    @Test
    @DisplayName("Get a list movie with filter returns a MatchingMovies list")
    @Order(7)
    void getListMovie_WithValidParams_ReturnsMatchingMovies() {

        movies.add(MOVIE);

        when(repository.findAll(ArgumentMatchers.<Specification<Movie>>any())).thenReturn(movies);

        var result = service.findAll(MOVIE.getTitle(), MOVIE.getFeatured());

        assertThat(result).contains(MOVIE);


    }

    @Test
    @DisplayName("Get an empty list movie with filter returns an empty list")
    @Order(7)
    void getEmptyListMovie_WithValidParams_ReturnsEmptyList() {

        when(repository.findAll(ArgumentMatchers.<Specification<Movie>>any())).thenReturn(Collections.emptyList());

        var result = service.findAll(null, null);

        assertThat(result).isEmpty();


    }


    @Test
    @DisplayName("Delete a movie by existing id dont throws exception")
    @Order(8)
    void deleteMovie_ByExistingTitle_DontThrowsException() {

        when(repository.findById(1L)).thenReturn(Optional.of(MOVIE));

        assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();
    }


    @Test
    @DisplayName("Delete a movie by unexisting id throws NotFoundException")
    @Order(9)
    void deleteMovie_ByUnexistingId_ThrowsNotFoundException() {
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(NotFoundException.class);

    }


}