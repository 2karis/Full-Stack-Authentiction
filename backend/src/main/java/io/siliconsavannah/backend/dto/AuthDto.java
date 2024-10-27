package io.siliconsavannah.backend.dto;

import lombok.Builder;

@Builder
public record AuthDto(
        int id,
        String email,
        String role,
        String token
) { }
