package io.siliconsavannah.backend.dto;

import java.math.BigDecimal;

public record SubscribeDto(
        int userId,
        BigDecimal monthlyFee
){}