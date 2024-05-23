package com.onesquad.reservation.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface IReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByAccommodationId(UUID accommodationId);
    List<ReservationEntity> findByUserId(UUID userId);

    @Query("SELECT r FROM ReservationEntity r WHERE r.accommodation.id = :accommodationId AND r.status = 'APPROVED'")
    List<ReservationEntity> findApprovedReservationsByAccommodationId(@Param("accommodationId") UUID accommodationId);
}
