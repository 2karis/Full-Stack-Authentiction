package io.siliconsavannah.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuthority implements Serializable {
    @JsonProperty("role_id")
    Long roleId;
    @JsonProperty("authority_id")
    Long authorityId;
}
