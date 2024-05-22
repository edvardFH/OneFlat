package com.onesquad.accommodation.adapter.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityId implements Serializable {
    @Column(name = "accommodation_id")
    private UUID accommodationId;
    private Date startDate;
    private Date endDate;
}