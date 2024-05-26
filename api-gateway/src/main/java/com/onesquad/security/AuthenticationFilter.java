package com.onesquad.security;

import com.onesquad.service.AuthenticationService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<FilterConfig> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationService authenticationService) {
        super(FilterConfig.class);
        this.authenticationService = authenticationService;
    }

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            String requestPath = exchange.getRequest().getURI().getPath();
            if(config.whitelist().stream().anyMatch(pattern -> pathMatcher.match(pattern, requestPath))) {
                return chain.filter(exchange);
            }

            String authorizationHeader = extractToken(exchange.getRequest());

            if (authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return authenticationService.validateToken(authorizationHeader)
                    .flatMap(isValid -> {
                        if (isValid) {
                            return chain.filter(exchange);
                        }
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    })
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }


    private String extractToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst(AUTHORIZATION_HEADER);
    }
}
