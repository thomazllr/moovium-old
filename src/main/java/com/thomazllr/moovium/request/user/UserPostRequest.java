package com.thomazllr.moovium.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPostRequest {

    private String fullName;
    private String nickname;
    private String email;
    private String password;
    private String avatarUrl;
    private String bio;
    private String status;
}
