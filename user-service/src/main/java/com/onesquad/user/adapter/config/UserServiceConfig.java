package com.onesquad.user.adapter.config;

import com.onesquad.user.adapter.persistence.IUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfig {

    private final IUserJpaRepository userJpaRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userJpaRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }
}
