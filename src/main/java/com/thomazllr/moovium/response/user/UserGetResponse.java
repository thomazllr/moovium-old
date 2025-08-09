package com.thomazllr.moovium.response.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserGetResponse {

    @Builder
    public record Role(String name) {
    }

    private Long id;
    private String fullName;
    private String nickname;
    private String email;
    private String avatarUrl;
    private String bio;
    private String status;
    private Set<Role> roles;

}


