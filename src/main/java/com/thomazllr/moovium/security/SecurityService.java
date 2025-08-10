package com.thomazllr.moovium.security;

import com.thomazllr.moovium.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof CustomAuthentication customAuth) {
            return customAuth.getUser();
        }
        return null;
    }
}
