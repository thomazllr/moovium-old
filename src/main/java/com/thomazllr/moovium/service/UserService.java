package com.thomazllr.moovium.service;

import com.thomazllr.moovium.commons.BannedWordsProvider;
import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Role;
import com.thomazllr.moovium.model.User;
import com.thomazllr.moovium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final BannedWordsProvider bannedWordsProvider;

    public User save(User user) {
        String encode = encoder.encode(user.getPasswordHash());
        user.setPasswordHash(encode);
        Role role = roleService.findByName("user");
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.setStatus("active");
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
        return repository.findByEmail(email);
    }

    @Transactional
    public void associate(Long userId, Long roleId) {
        var user = findByIdOrBadRequest(userId);
        var role = roleService.findByIdOrBadRequest(roleId);
        user.getRoles().add(role);
        repository.save(user);
    }

    @Transactional
    public void dissociate(Long userId, Long roleId) {
        var user = findByIdOrBadRequest(userId);
        var role = roleService.findByIdOrBadRequest(roleId);
        user.getRoles().remove(role);
        repository.save(user);
    }

    public boolean isNicknameAvailable(String rawNickname) {
        validateNickName(rawNickname);
        return !repository.existsByNicknameIgnoreCase(rawNickname);
    }

    public void validateNickName(String rawNickname) {
        if (rawNickname == null || rawNickname.isBlank()) {
            throw new BusinessException("Nickname cannot be empty.");
        }

        String nick = validateAndNormalizeNickname(rawNickname);
        validateBannedWords(nick);
    }

    private String validateAndNormalizeNickname(String rawNickname) {
        String nick = rawNickname.trim();

        if (nick.length() <= 3) {
            throw new BusinessException("Nickname '%s' is too short.".formatted(nick));
        }
        if (nick.length() >= 15) {
            throw new BusinessException("Nickname '%s' is too long.".formatted(nick));
        }

        if (!nick.matches("^[a-zA-Z0-9_-]+$")) {
            throw new BusinessException("Nickname '%s' contains invalid characters.".formatted(nick));
        }

        if (nick.matches("^[0-9]+$")) {
            throw new BusinessException("Nickname '%s' cannot be only numbers.".formatted(nick));
        }

        return nick;
    }

    private void validateBannedWords(String nick) {
        String normalizedNick = Normalizer.normalize(nick, Normalizer.Form.NFKC).toLowerCase();

        if (bannedWordsProvider.containsIn(normalizedNick)) {
            throw new BusinessException("Nickname '%s' contains a prohibited word.".formatted(nick));
        }
    }


}
