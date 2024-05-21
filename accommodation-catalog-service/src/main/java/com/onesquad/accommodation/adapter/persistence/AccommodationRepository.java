package com.onesquad.accommodation.adapter.persistence;

import com.onesquad.accommodation.adapter.mapper.AccommodationEntityMapper;
import com.onesquad.accommodation.adapter.mapper.UserEntityMapper;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
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
    public List<Accommodation> searchAccommodations(AccommodationType type, String city, Price minPrice, Price maxPrice) {
        return jpaAccommodationRepository.searchAccommodations(
                        type.toString(),
                        city,
                        minPrice != null ? minPrice.value() : null,
                        maxPrice != null ? maxPrice.value() : null
                ).stream()
                .map(entity -> {
                    UserEntity userEntity = entity.getOwner();
                    return AccommodationEntityMapper.toDomain(entity, UserEntityMapper.toDomain(userEntity));
                })
                .collect(Collectors.toList());
    }
}

