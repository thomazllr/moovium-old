package com.thomazllr.moovium.service;

import com.thomazllr.moovium.model.Client;
import com.thomazllr.moovium.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client save(Client client) {

        String password = client.getClientSecret();

        var hashPassword = encoder.encode(password);

        client.setClientSecret(hashPassword);

        return repository.save(client);
    }

    public Client findByClientId(String id) {
        return repository.findByClientId(id);
    }
}
