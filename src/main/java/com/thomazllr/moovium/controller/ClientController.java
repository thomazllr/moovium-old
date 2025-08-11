package com.thomazllr.moovium.controller;

import com.thomazllr.moovium.mapper.ClientMapper;
import com.thomazllr.moovium.request.client.ClientPostRequest;
import com.thomazllr.moovium.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody ClientPostRequest request) {

        log.info("Register a new client: {} with scope {}", request.getClientId(), request.getScope());

        var client = mapper.toEntity(request);
        service.save(client);
        return ResponseEntity.noContent().build();
    }
}
