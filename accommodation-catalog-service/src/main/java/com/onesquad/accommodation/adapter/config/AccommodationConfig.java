package com.onesquad.accommodation.adapter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan(basePackages = {
        "com.onesquad.accommodation.adapter.persistence",
        "com.onesquad.user.adapter.persistence"
})
public class AccommodationConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
