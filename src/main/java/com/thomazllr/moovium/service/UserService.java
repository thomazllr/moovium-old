package com.thomazllr.moovium.service;

import com.thomazllr.moovium.model.User;
import com.thomazllr.moovium.repository.RoleRepository;
import com.thomazllr.moovium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public User save(User user) {
        String encode = encoder.encode(user.getPasswordHash());
        user.setPasswordHash(encode);
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAllWithRoles();
    }

}
