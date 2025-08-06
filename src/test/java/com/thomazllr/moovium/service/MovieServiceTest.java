package com.thomazllr.moovium.service;

import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.thomazllr.moovium.common.MovieConstants.INVALID_MOVIE;
import static com.thomazllr.moovium.common.MovieConstants.MOVIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Test
    @DisplayName("Create a movie with valid data returns a movie")
    void createMovie_WithValidData_ReturnsAMovie() {

        when(repository.save(MOVIE)).thenReturn(MOVIE);

        Movie sut = service.save(MOVIE);

        assertThat(sut).isEqualTo(MOVIE);
    }

    @Test
    @DisplayName("Create a movie with invalid data throws Exception")
    void createMovie_WithInvalidData_ThrowsException() {

        when(repository.save(INVALID_MOVIE)).thenThrow(new RuntimeException("Invalid data"));

        assertThatThrownBy(() -> service.save(INVALID_MOVIE)).isInstanceOf(RuntimeException.class);


    }

}