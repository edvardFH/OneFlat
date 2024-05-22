package com.onesquad.accommodation.adapter.mapper;

import com.onesquad.accommodation.adapter.dto.AvailabilityDTO;
import com.onesquad.accommodation.adapter.persistence.AvailabilityEntity;
import com.onesquad.accommodation.adapter.persistence.AvailabilityId;
import com.onesquad.accommodation.domain.Availability;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AvailabilityMapper {

    public static AvailabilityEntity toEntity(Availability availability, UUID accommodationId) {
        return new AvailabilityEntity(
                new AvailabilityId(accommodationId, availability.startDate(), availability.endDate()),
                null
        );
    }

    public static Availability toDomain(AvailabilityEntity entity) {
        return new Availability(
                entity.getId().getStartDate(),
                entity.getId().getEndDate()
        );
    }

    public static AvailabilityDTO toDTO(Availability availability) {
        return new AvailabilityDTO(availability.startDate(), availability.endDate());
    }

    public static Availability toDomain(AvailabilityDTO dto) {
        return new Availability(dto.startDate(), dto.endDate());
    }

    public static Set<AvailabilityEntity> toEntitySet(Set<Availability> availabilities, UUID accommodationId) {
        return availabilities.stream()
                .map(availability -> toEntity(availability, accommodationId))
                .collect(Collectors.toSet());
    }

    public static Set<Availability> toDomainSetFromEntity(Set<AvailabilityEntity> availabilityEntities) {
        return availabilityEntities.stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toSet());
    }

    public static Set<AvailabilityDTO> toDTOSet(Set<Availability> availabilities) {
        return availabilities.stream()
                .map(AvailabilityMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public static Set<Availability> toDomainSetFromDTO(Set<AvailabilityDTO> availabilityDTOs) {
        return availabilityDTOs.stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toSet());
    }
}
