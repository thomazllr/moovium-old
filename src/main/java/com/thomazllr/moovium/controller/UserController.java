package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.dto.AssociateUserRoleDTO;
import com.thomazllr.moovium.dto.DissociateUserRoleDTO;
import com.thomazllr.moovium.mapper.UserMapper;
import com.thomazllr.moovium.request.user.UserPostRequest;
import com.thomazllr.moovium.response.user.UserGetResponse;
import com.thomazllr.moovium.response.user.UserPostResponse;
import com.thomazllr.moovium.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/associate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> associate(@RequestBody @Valid AssociateUserRoleDTO dto) {
        service.associate(dto.userId(), dto.roleId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/dissociate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> associate(@RequestBody @Valid DissociateUserRoleDTO dto) {
        service.dissociate(dto.userId(), dto.roleId());
        return ResponseEntity.noContent().build();
    }
}
