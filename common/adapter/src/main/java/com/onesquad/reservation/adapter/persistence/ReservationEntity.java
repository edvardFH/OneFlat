package com.onesquad.reservation.adapter.persistence;

import com.onesquad.accommodation.adapter.persistence.AccommodationEntity;
import com.onesquad.reservation.domain.ReservationStatus;
import com.onesquad.user.adapter.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private AccommodationEntity accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private String comment;
}