package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.Movie;
import com.thomazllr.moovium.model.Session;
import com.thomazllr.moovium.model.Theater;
import com.thomazllr.moovium.request.session.SessionPostRequest;
import com.thomazllr.moovium.response.session.SessionGetResponse;
import com.thomazllr.moovium.response.session.SessionPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "movie", source = "movieId")
    @Mapping(target = "theater", source = "theaterId")
    Session toSession(SessionPostRequest request);

    SessionPostResponse toSessionPostResponse(Session session);

    @Mapping(target = "title", source = "movie.title")
    @Mapping(target = "theaterName", source = "theater.name")
    SessionGetResponse toSessionGetResponse(Session session);

    List<SessionGetResponse> toSessionGetResponseList(List<Session> sessions);

    default Movie mapMovie(Long movieId) {
        if (movieId == null) return null;
        Movie movie = new Movie();
        movie.setId(movieId);
        return movie;
    }

    default Theater mapTheater(Long theaterId) {
        if (theaterId == null) return null;
        Theater theater = new Theater();
        theater.setId(theaterId);
        return theater;
    }
}
