package com.onesquad.accommodation.adapter.dto;

import java.util.Date;

public record UpdateAvailabilityRequestDTO(
        Date oldStartDate,
        Date oldEndDate,
        Date newStartDate,
        Date newEndDate) {}
