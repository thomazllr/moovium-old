package com.thomazllr.moovium.request.theater;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TheaterPostRequest {

    @NotBlank(message = "Name is required.")
    private String name;
    @NotNull(message = "Capacity is required.")
    private Integer capacity;
    @NotBlank(message = "Room type is required.")
    private String roomType;
}
