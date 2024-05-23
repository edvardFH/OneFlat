package com.onesquad.reservation.adapter.persistence;


import com.onesquad.common.exception.NotFoundException;
import com.onesquad.reservation.adapter.mapper.ReservationMapper;
import com.onesquad.reservation.application.repository.IReservationRepository;
import com.onesquad.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ReservationRepository implements IReservationRepository {

    private final IReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = ReservationMapper.toEntity(reservation);
        ReservationEntity savedEntity = reservationJpaRepository.save(entity);
        return ReservationMapper.toDomain(savedEntity);
    }

    @Override
    public Reservation update(Reservation reservation) {
        Optional<ReservationEntity> reservationEntity = reservationJpaRepository.findById(reservation.id());

        if(reservationEntity.isEmpty()) {
            throw new NotFoundException("Reservation with id " + reservation.id() + " not found");
        }

        ReservationEntity existingReservation = reservationEntity.get();

        existingReservation.setComment(reservation.comment());
        existingReservation.setStartDate(reservation.startDate());
        existingReservation.setEndDate(reservation.endDate());
        existingReservation.setStatus(reservation.status());

        return ReservationMapper.toDomain(reservationJpaRepository.save(existingReservation));
    }

    @Override
    public Optional<Reservation> findById(UUID reservationId) {
        return reservationJpaRepository.findById(reservationId)
                .map(ReservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findByAccommodationId(UUID accommodationId) {
        return reservationJpaRepository.findByAccommodationId(accommodationId)
                .stream()
                .map(ReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return reservationJpaRepository.findByUserId(userId)
                .stream()
                .map(ReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID reservationId) {
        reservationJpaRepository.deleteById(reservationId);
    }
}

