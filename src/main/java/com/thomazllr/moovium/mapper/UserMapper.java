package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.User;
import com.thomazllr.moovium.request.user.UserPostRequest;
import com.thomazllr.moovium.response.user.UserGetResponse;
import com.thomazllr.moovium.response.user.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "password", target = "passwordHash")
    User toEntity(UserPostRequest request);

    UserPostResponse toUserPostResponse(User user);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);
}
