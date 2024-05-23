package com.onesquad.accommodation.adapter.client;


import com.onesquad.accommodation.domain.Accommodation;

import java.util.Optional;
import java.util.UUID;

public interface IAccommodationServiceClient {
    Optional<Accommodation> getAccommodationById(UUID accommodationId);
}
