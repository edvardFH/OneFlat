package com.onesquad.reservation.adapter.config;

import com.onesquad.accommodation.adapter.client.AccommodationServiceClient;
import com.onesquad.accommodation.adapter.client.IAccommodationServiceClient;
import com.onesquad.common.controller.GlobalExceptionHandler;
import com.onesquad.user.adapter.client.IUserServiceClient;
import com.onesquad.user.adapter.client.UserServiceClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan(basePackages = {
        "com.onesquad.accommodation.adapter.persistence",
        "com.onesquad.user.adapter.persistence",
        "com.onesquad.reservation.adapter.persistence"
})
public class ReservationApplicationConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IUserServiceClient userServiceClient(RestTemplate restTemplate) {
        return new UserServiceClient(restTemplate);
    }

    @Bean
    public IAccommodationServiceClient accommodationServiceClient(
            RestTemplate restTemplate,
            IUserServiceClient userServiceClient) {
        return new AccommodationServiceClient(restTemplate, userServiceClient);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
