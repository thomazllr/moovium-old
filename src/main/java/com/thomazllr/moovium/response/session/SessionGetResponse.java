package com.thomazllr.moovium.response.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SessionGetResponse {

    private UUID id;
    private String title;
    private String theaterName;
    private String sessionTime;
}
