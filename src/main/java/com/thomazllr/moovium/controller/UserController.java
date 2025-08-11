package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.mapper.UserMapper;
import com.thomazllr.moovium.request.user.UserPostRequest;
import com.thomazllr.moovium.response.user.UserGetResponse;
import com.thomazllr.moovium.response.user.UserPostResponse;
import com.thomazllr.moovium.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations about users and user management")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system"
    )
    @ApiResponse(responseCode = "200", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "User already exists or nickname/email not available")
    public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
        var user = mapper.toEntity(request);
        var response = mapper.toUserPostResponse(service.save(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users in the system"
    )
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserGetResponse>> get() {
        var users = service.findAll();
        var response = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Associate user with role",
            description = "Assign a specific role to a user"
    )
    @ApiResponse(responseCode = "204", description = "Role associated successfully")
    @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    @ApiResponse(responseCode = "404", description = "User or role not found")
    @ApiResponse(responseCode = "409", description = "User already has this role")
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long roleId) {
        service.associate(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Dissociate user from role",
            description = "Remove a specific role from a user"
    )
    @ApiResponse(responseCode = "204", description = "Role dissociated successfully")
    @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    @ApiResponse(responseCode = "404", description = "User or role not found")
    @ApiResponse(responseCode = "409", description = "User does not have this role")
    public ResponseEntity<Void> dissociate(@PathVariable Long userId, @PathVariable Long roleId) {
        service.dissociate(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nicknames/availability")
    @Operation(
            summary = "Check nickname availability",
            description = "Verify if a nickname is available for use during user registration"
    )
    @ApiResponse(responseCode = "200", description = "Nickname availability checked successfully")
    @ApiResponse(responseCode = "400", description = "Invalid nickname format")
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestParam String nickname) {
        boolean available = service.isNicknameAvailable(nickname);
        return ResponseEntity.ok(Map.of(
                "nickname", nickname,
                "available", available
        ));
    }
}