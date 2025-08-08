package com.thomazllr.moovium.request.session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionPostRequest {

    private Integer movieId;
    private Integer theaterId;
    private LocalDateTime sessionTime;
}
