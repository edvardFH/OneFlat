package com.onesquad.reservation.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface IReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByAccommodationId(UUID accommodationId);
    List<ReservationEntity> findByUserId(UUID userId);
}
