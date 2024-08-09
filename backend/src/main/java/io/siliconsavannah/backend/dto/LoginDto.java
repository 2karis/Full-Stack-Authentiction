package io.siliconsavannah.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record LoginDto(
        String email,
        String password) {

}
