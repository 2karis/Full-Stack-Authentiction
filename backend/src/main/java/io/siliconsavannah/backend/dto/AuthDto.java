package io.siliconsavannah.backend.dto;

import io.siliconsavannah.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record AuthDto(
        String email,
        String role,
        String token
) { }
