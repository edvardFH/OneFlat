package com.onesquad.accommodation.adapter.mapper;


import com.onesquad.accommodation.adapter.persistence.LocationEntity;
import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.accommodation.domain.*;
import com.onesquad.accommodation.adapter.persistence.AccommodationEntity;
import com.onesquad.user.domain.User;

public class AccommodationEntityMapper {

    public static Accommodation toDomain(AccommodationEntity entity, User owner) {
        Location location = new Location(
                entity.getLocation().getStreet(),
                entity.getLocation().getCity(),
                entity.getLocation().getPostalCode(),
                entity.getLocation().getCountry()
        );
        return new Accommodation(
                entity.getId(),
                owner,
                entity.getType(),
                location,
                new Price(entity.getPrice()),
                entity.getNumberOfRooms(),
                entity.getNumberOfBathrooms(),
                new Area(entity.getArea()),
                entity.getDescription(),
                entity.isVisible()
        );
    }

    public static AccommodationEntity toEntity(Accommodation accommodation, UserEntity ownerEntity) {
        LocationEntity location = new LocationEntity(
                null,
                accommodation.location().street(),
                accommodation.location().city(),
                accommodation.location().postalCode(),
                accommodation.location().country()
        );
        return new AccommodationEntity(
                accommodation.id(),
                ownerEntity,
                accommodation.type(),
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
