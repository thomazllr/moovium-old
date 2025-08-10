package com.thomazllr.moovium.security;

import com.thomazllr.moovium.model.User;
import com.thomazllr.moovium.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String DEFAULT_BIO = "Hello, I'm a new user. I'm still learning how to use this moovium. I hope you enjoy it!";
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String email = oauthToken.getPrincipal().getAttributes().get("email").toString();

        var user = getUserByEmail(email);

        if (Objects.isNull(user)) {
            user = User.builder()
                    .bio(DEFAULT_BIO)
                    .fullName(email)
                    .nickname(getNickNameByEmail(email))
                    .email(email)
                    .passwordHash("123")
                    .build();
            user = userService.save(user);
        }

        CustomAuthentication customAuth = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(customAuth);

        super.onAuthenticationSuccess(request, response, customAuth);
    }


    public User getUserByEmail(String email) {
        return userService.findByEmailOrThrow(email);
    }

    public String getNickNameByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }



}
