package io.siliconsavannah.backend.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ContentDto(
    Long id,
    String title,
    String description,
    String content,
    String username,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
