package com.thomazllr.moovium.config;

import com.thomazllr.moovium.security.CustomUserDetailsService;
import com.thomazllr.moovium.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers(HttpMethod.POST, "v1/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "v1/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "v1/movies/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "v1/theaters/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "v1/sessions").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService service) {
        return new CustomUserDetailsService(service);
    }

}
