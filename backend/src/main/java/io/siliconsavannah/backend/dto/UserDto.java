package io.siliconsavannah.backend.dto;

import lombok.Builder;

@Builder
public record UserDto(
        int id,
        String firstname,
        String lastname,
        String email,
        String role) {
}
