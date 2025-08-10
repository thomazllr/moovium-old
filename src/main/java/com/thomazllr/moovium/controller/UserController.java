package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.mapper.UserMapper;
import com.thomazllr.moovium.request.user.UserPostRequest;
import com.thomazllr.moovium.response.user.UserGetResponse;
import com.thomazllr.moovium.response.user.UserPostResponse;
import com.thomazllr.moovium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest request) {
        var user = mapper.toEntity(request);
        var response = mapper.toUserPostResponse(service.save(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> get() {
        var users = service.findAll();
        var response = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long roleId) {
        service.associate(userId, roleId);
        return ResponseEntity.noContent().build(); // ou 201 se quiser Location
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> dissociate(@PathVariable Long userId, @PathVariable Long roleId) {
        service.dissociate(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nicknames/availability")
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestParam String nickname) {
            boolean available = service.isNicknameAvailable(nickname);
            return ResponseEntity.ok(Map.of(
                    "nickname", nickname,
                    "available", available
            ));
    }
}
