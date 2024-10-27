package io.siliconsavannah.backend.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String firstname,
        String lastname,
        String email,
        String role) {
}
