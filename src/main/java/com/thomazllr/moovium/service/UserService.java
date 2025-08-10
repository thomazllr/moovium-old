package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Role;
import com.thomazllr.moovium.model.User;
import com.thomazllr.moovium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public User save(User user) {
        String encode = encoder.encode(user.getPasswordHash());
        user.setPasswordHash(encode);
        Role role = roleService.findByName("user");
        System.out.println(role.getName());
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAllWithRoles();
    }

    public User findByNicknameWithRoles(String nickname) {
        return repository.findByNicknameWithRoles(nickname);
    }

    public User findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: %s".formatted(id)));
    }

    public User findByIdOrBadRequest(Long userId) {
        try {
            return findByIdOrThrow(userId);
        } catch (NotFoundException e) {
            throw new BusinessException("Invalid User Id: '%d'".formatted(userId));
        }
    }

    public User findByEmailOrThrow(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: %s".formatted(email)));
    }

    public void associate(Long userId, Long roleId) {
        var user = findByIdOrBadRequest(userId);
        var role = roleService.findByIdOrBadRequest(roleId);
        user.getRoles().add(role);
        repository.save(user);
    }

    public void dissociate(Long userId, Long roleId) {
        var user = findByIdOrBadRequest(userId);
        var role = roleService.findByIdOrBadRequest(roleId);
        user.getRoles().remove(role);
        repository.save(user);
    }

}
