package com.onesquad.accommodation.adapter.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "availabilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityEntity {

    @EmbeddedId
    private AvailabilityId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_id", insertable = false, updatable = false)
    @ToString.Exclude
    private AccommodationEntity accommodation;
}
