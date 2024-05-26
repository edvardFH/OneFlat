package com.onesquad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private static final String AUTH_SERVICE_URL = "http://USER-SERVICE/api/v1/auth/validate";
    private static final String AUTH_HEADER = "Authorization";

    private final WebClient.Builder webClientBuilder;

    public Mono<Boolean> validateToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri(AUTH_SERVICE_URL)
                .header(AUTH_HEADER, token)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
    }
}
