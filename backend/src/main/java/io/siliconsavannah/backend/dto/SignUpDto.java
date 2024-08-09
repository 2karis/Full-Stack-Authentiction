package io.siliconsavannah.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record SignUpDto(
        String firstname,
        String lastname,
        String email,
        String password) {

}
