package com.onesquad.accommodation.adapter.persistence;

import com.onesquad.accommodation.domain.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IAccommodationJpaRepository extends JpaRepository<AccommodationEntity, UUID> {
    List<AccommodationEntity> findByOwnerId(UUID ownerId);

    @Query("SELECT a FROM AccommodationEntity a WHERE " +
            "(:type IS NULL OR a.type = :type) AND " +
            "(:city IS NULL OR a.location.city like :city) AND " +
            "(:minPrice IS NULL OR a.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR a.price <= :maxPrice) AND " +
            "a.isVisible is true ")
    List<AccommodationEntity> searchAccommodations(
            @Param("type") AccommodationType type,
            @Param("city") String city,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
