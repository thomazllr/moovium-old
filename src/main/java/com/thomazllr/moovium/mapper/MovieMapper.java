package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.model.Session;
import com.thomazllr.moovium.request.movie.MoviePostRequest;
import com.thomazllr.moovium.request.movie.MoviePutRequest;
import com.thomazllr.moovium.response.movie.MovieGetResponse;
import com.thomazllr.moovium.response.movie.MoviePostResponse;
import com.thomazllr.moovium.response.movie.MovieSessionsGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MoviePostResponse toMoviePostResponse(Movie movie);

    Movie toEntity(MoviePostRequest request);

    MovieGetResponse toMovieGetResponse(Movie movie);

    List<MovieGetResponse> toMovieGetResponseList(List<Movie> movies);

    @Mapping(target = "id", ignore = true)
    void updateMovieFromPutRequest(MoviePutRequest request, @MappingTarget Movie movie);

    MovieSessionsGetResponse toMovieSessionsGetResponse(Movie movie);

    @Mapping(target = "theaterName", source = "theater.name")
    MovieSessionsGetResponse.Session toSessionRecord(Session session);
}
