package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.ClientMapper;
import com.thomazllr.moovium.request.client.ClientPostRequest;
import com.thomazllr.moovium.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody ClientPostRequest request) {
        var client = mapper.toEntity(request);
        service.save(client);
        return ResponseEntity.noContent().build();
    }
}
