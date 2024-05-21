package com.onesquad.accommodation.adapter.mapper;

import com.onesquad.accommodation.adapter.dto.AccommodationRequestDTO;
import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.dto.LocationDTO;
import com.onesquad.accommodation.domain.*;
import com.onesquad.user.domain.User;

import java.util.UUID;

public class AccommodationDTOMapper {
    public static Accommodation toDomain(AccommodationRequestDTO dto, User owner) {
        return toDomainHelper(dto, null, owner);
    }

    public static Accommodation toDomain(AccommodationRequestDTO dto, UUID id, User owner) {
        return toDomainHelper(dto, id, owner);
    }

    private static Accommodation toDomainHelper(AccommodationRequestDTO dto, UUID id, User owner) {
        Location location = new Location(
                dto.location().street(),
                dto.location().city(),
                dto.location().postalCode(),
                dto.location().country()
        );
        return new Accommodation(
                id,
                owner,
                AccommodationType.valueOf(dto.type()),
                location,
                new Price(dto.price()),
                dto.numberOfRooms(),
                dto.numberOfBathrooms(),
                new Area(dto.area()),
                dto.description(),
                dto.isVisible()
        );
    }



    public static Accommodation toDomain(AccommodationResponseDTO dto, User owner) {
        Location location = new Location(
                dto.location().street(),
                dto.location().city(),
                dto.location().postalCode(),
                dto.location().country()
        );
        return new Accommodation(
                dto.id(),
                owner,
                AccommodationType.valueOf(dto.type()),
                location,
                new Price(dto.price()),
                dto.numberOfRooms(),
                dto.numberOfBathrooms(),
                new Area(dto.area()),
                dto.description(),
                dto.isVisible()
        );
    }

    public static AccommodationResponseDTO toDTO(Accommodation accommodation) {
        LocationDTO location = new LocationDTO(
                accommodation.location().street(),
                accommodation.location().city(),
                accommodation.location().postalCode(),
                accommodation.location().country()
        );
        return new AccommodationResponseDTO(
                accommodation.id(),
                accommodation.owner().id(),
                accommodation.type().name(),
                location,
                accommodation.price().value(),
                accommodation.numberOfRooms(),
                accommodation.numberOfBathrooms(),
                accommodation.area().value(),
                accommodation.description(),
                accommodation.isVisible()
        );
    }
}
