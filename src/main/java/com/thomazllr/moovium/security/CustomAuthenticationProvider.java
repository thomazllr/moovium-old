package com.thomazllr.moovium.security;

import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService service;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        var user = service.findByNicknameWithRoles(login);

        if (user == null) {
            throw getNotUserFoundException();
        }

        String hashPassword = user.getPasswordHash();

        boolean matches = encoder.matches(password, hashPassword);

        if (matches) {
            return new CustomAuthentication(user);
        }

        throw getNotUserFoundException();
    }

    private static NotFoundException getNotUserFoundException() {
        return new NotFoundException("User or password incorrect.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
