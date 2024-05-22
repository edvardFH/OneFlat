package com.onesquad.accommodation.adapter.persistence;


import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.user.adapter.persistence.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "accommodations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    private Double price;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private int area;
    private String description;
    private boolean isVisible;
}
