package com.onesquad.accommodation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/accommodations")
public record AccommodationController(AccommodationService accommodationService) {

    @PostMapping
    public void registerAccommodation(@RequestBody AccommodationRegistrationRequest accommodationRegistrationRequest) {
        log.info("New accommodation registered {}", accommodationRegistrationRequest);
        accommodationService.registerAccommodation(accommodationRegistrationRequest);
    }
}
