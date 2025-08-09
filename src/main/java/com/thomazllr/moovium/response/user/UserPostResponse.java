package com.thomazllr.moovium.response.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPostResponse {

    private Long id;
    private String fullName;
    private String nickname;
    private String email;
    private String avatarUrl;
    private String bio;
    private String status;


}
