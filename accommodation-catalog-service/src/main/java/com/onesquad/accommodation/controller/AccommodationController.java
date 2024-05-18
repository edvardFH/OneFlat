package com.onesquad.accommodation.controller;

import com.onesquad.accommodation.entity.Accommodation;
import com.onesquad.accommodation.dto.AccommodationRegistrationRequest;
import com.onesquad.accommodation.service.AccommodationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/accommodations")
@AllArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @PostMapping
    public void registerAccommodation(@RequestBody AccommodationRegistrationRequest accommodationRegistrationRequest) {
        log.info("New accommodation registered {}", accommodationRegistrationRequest);
        accommodationService.registerAccommodation(accommodationRegistrationRequest);
    }

    @GetMapping(path = "{accommodationId}")
    public ResponseEntity<Accommodation> getAccommodationById(@RequestParam int accommodationId) {
        return accommodationService.getAccommodationById(accommodationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
