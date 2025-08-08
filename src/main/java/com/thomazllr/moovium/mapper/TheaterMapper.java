package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.Theater;
import com.thomazllr.moovium.request.theater.TheaterPostRequest;
import com.thomazllr.moovium.response.theater.TheaterGetResponse;
import com.thomazllr.moovium.response.theater.TheaterPostResponse;
import com.thomazllr.moovium.response.theater.TheaterSeatsGetResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TheaterMapper {

    TheaterPostResponse toTheaterPostResponse(Theater theater);

    Theater toEntity(TheaterPostRequest request);

    TheaterGetResponse toTheaterGetResponse(Theater theater);

    TheaterSeatsGetResponse toTheaterSeatsGetResponse(Theater theater);

    List<TheaterGetResponse> toTheaterGetResponseList(List<Theater> theaters);

}
