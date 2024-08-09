package io.siliconsavannah.backend.dto;

public record UserDto(
        int id,
        String firstname,
        String lastname,
        String email,
        String role) {
}
