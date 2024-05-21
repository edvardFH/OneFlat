package com.onesquad.accommodation.adapter.persistence;


import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.accommodation.domain.AccommodationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(name = "accommodations")
@Getter
@Setter
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
