package com.onesquad.accommodation.adapter.persistence;

import com.onesquad.accommodation.adapter.mapper.AccommodationEntityMapper;
import com.onesquad.accommodation.adapter.mapper.UserEntityMapper;
import com.onesquad.accommodation.application.exception.NotFoundException;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Location;
import com.onesquad.accommodation.domain.Price;
import com.onesquad.user.adapter.persistence.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccommodationRepository implements IAccommodationRepository {

    private IAccommodationJpaRepository jpaAccommodationRepository;

    @Override
    public Optional<Accommodation> findById(UUID id) {
        return jpaAccommodationRepository.findById(id).map(entity -> {
            UserEntity userEntity = entity.getOwner();
            return AccommodationEntityMapper.toDomain(entity, UserEntityMapper.toDomain(userEntity));
        });
    }

    @Override
    public List<Accommodation> findByOwnerId(UUID ownerId) {
        return jpaAccommodationRepository.findByOwnerId(ownerId).stream()
                .map(entity -> {
                    UserEntity userEntity = entity.getOwner();
                    return AccommodationEntityMapper.toDomain(entity, UserEntityMapper.toDomain(userEntity));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        AccommodationEntity entity = AccommodationEntityMapper.toEntity(accommodation, UserEntityMapper.toEntity(accommodation.owner()));
        return AccommodationEntityMapper.toDomain(jpaAccommodationRepository.save(entity), accommodation.owner());
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        Optional<AccommodationEntity> accommodationEntity = jpaAccommodationRepository.findById(accommodation.id());

        if (accommodationEntity.isEmpty()) {
            throw new NotFoundException("Accommodation with id " + accommodation.id() + " not found");
        }

        AccommodationEntity existingAccommodation = accommodationEntity.get();
        LocationEntity existingLocation = existingAccommodation.getLocation();

        // Update the properties of the existing accommodation
        existingAccommodation.setType(accommodation.type());
        existingLocation.setCity(accommodation.location().city());
        existingLocation.setCountry(accommodation.location().country());
        existingLocation.setStreet(accommodation.location().street());
        existingLocation.setPostalCode(accommodation.location().postalCode());
        existingAccommodation.setPrice(accommodation.price().value());
        existingAccommodation.setNumberOfRooms(accommodation.numberOfRooms());
        existingAccommodation.setNumberOfBathrooms(accommodation.numberOfBathrooms());
        existingAccommodation.setArea(accommodation.area().value());
        existingAccommodation.setDescription(accommodation.description());

        return AccommodationEntityMapper.toDomain(
                jpaAccommodationRepository.save(existingAccommodation), accommodation.owner()
        );
    }

    @Override
    public List<Accommodation> searchAccommodations(
            Optional<AccommodationType> type,
            Optional<String> city,
            Optional<Price> minPrice,
            Optional<Price> maxPrice) {
        return jpaAccommodationRepository.searchAccommodations(
                        type.orElse(null),
                        city.orElse(null),
                        minPrice.map(Price::value).orElse(null),
                        maxPrice.map(Price::value).orElse(null)
                ).stream()
                .map(entity -> {
                    UserEntity userEntity = entity.getOwner();
                    return AccommodationEntityMapper.toDomain(entity, UserEntityMapper.toDomain(userEntity));
                })
                .collect(Collectors.toList());
    }
}

