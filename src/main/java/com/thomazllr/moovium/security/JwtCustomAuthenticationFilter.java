package com.thomazllr.moovium.security;

import com.thomazllr.moovium.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (shouldConverter(auth)) {
            String login = auth.getName();
            var user = service.findByNicknameWithRoles(login);

            if (user != null) {
                auth = new CustomAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);

    }

    private boolean shouldConverter(Authentication auth) {
        return auth != null && auth instanceof JwtAuthenticationToken;
    }
}
