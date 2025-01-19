package io.siliconsavannah.backend.dto;

import java.time.LocalDateTime;
public record CustomerDto(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String profile,
        String address,
        String country,
        String state,
        String city,
        String zipCode,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean isActive
) {}
