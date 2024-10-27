package io.siliconsavannah.backend.dto;

import lombok.Builder;

@Builder
public record AuthDto(
        String email,
        String role,
        String token
) { }
