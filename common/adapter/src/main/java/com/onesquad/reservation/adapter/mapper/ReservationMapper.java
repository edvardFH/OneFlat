package com.onesquad.reservation.adapter.mapper;

import com.onesquad.accommodation.adapter.mapper.AccommodationEntityMapper;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.reservation.adapter.dto.ReservationRequestDTO;
import com.onesquad.reservation.adapter.dto.ReservationResponseDTO;
import com.onesquad.reservation.adapter.persistence.ReservationEntity;
import com.onesquad.reservation.domain.Reservation;
import com.onesquad.reservation.domain.ReservationStatus;
import com.onesquad.user.adapter.mapper.UserEntityMapper;
import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.user.domain.User;

import java.util.UUID;

public class ReservationMapper {

    public static Reservation toDomain(ReservationEntity entity) {
        User user = UserEntityMapper.toDomain(entity.getUser());
        return new Reservation(
                entity.getId(),
                AccommodationEntityMapper.toDomain(entity.getAccommodation(), user),
                user,
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus(),
                entity.getComment()
        );
    }

    public static ReservationEntity toEntity(Reservation domain) {
        UserEntity user = UserEntityMapper.toEntity(domain.user());
        return new ReservationEntity(
                domain.id(),
                AccommodationEntityMapper.toEntity(domain.accommodation(), user),
                user,
                domain.startDate(),
                domain.endDate(),
                domain.status(),
                domain.comment()
        );
    }

    public static ReservationResponseDTO toDTO(Reservation domain) {
        return new ReservationResponseDTO(
                domain.id(),
                domain.accommodation().id(),
                domain.user().id(),
                domain.startDate(),
                domain.endDate(),
                domain.status(),
                domain.comment()
        );
    }

    public static Reservation toDomain(ReservationRequestDTO dto, UUID id, Accommodation accommodation, User user) {
        return new Reservation(
                id,
                accommodation,
                user,
                dto.startDate(),
                dto.endDate(),
                ReservationStatus.PENDING,
                dto.comment()
        );
    }
}
