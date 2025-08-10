package com.thomazllr.moovium.request.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientPostRequest {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
}
