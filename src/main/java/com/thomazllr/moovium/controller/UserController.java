package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.UserMapper;
import com.thomazllr.moovium.request.user.UserPostRequest;
import com.thomazllr.moovium.response.user.UserGetResponse;
import com.thomazllr.moovium.response.user.UserPostResponse;
import com.thomazllr.moovium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest request) {
        var user = mapper.toEntity(request);
        var response = mapper.toUserPostResponse(userService.save(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> get() {
        var users = userService.findAll();
        var response = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(response);
    }
}
