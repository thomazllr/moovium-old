package com.thomazllr.moovium.response.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SessionPostResponse {

    private UUID id;
}
