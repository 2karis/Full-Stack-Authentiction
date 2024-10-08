package io.siliconsavannah.backend.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record PaymentDto(
        long invoiceId,
        long subscriptionId,
        BigDecimal amount,
        String type, // e.g., Credit Card, Bank Transfer
        String details
) {}
