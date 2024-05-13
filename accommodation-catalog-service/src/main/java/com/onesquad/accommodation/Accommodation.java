package com.onesquad.accommodation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {
    @Id
    @SequenceGenerator(
            name = "accommodation_id_sequence",
            sequenceName = "accommodation_id_sequence"
    )
    @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "customer_id_sequence"
    )
    private Long id;
    private Long ownerId;
    private String type;
    private String location;
    private double price;
    private int bedrooms;
    private int bathrooms;
    private double area;
    private String description;
}