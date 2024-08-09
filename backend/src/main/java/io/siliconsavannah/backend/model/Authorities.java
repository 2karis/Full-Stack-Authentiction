package io.siliconsavannah.backend.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
@Data
public class Authorities implements GrantedAuthority {
    private String authority;
}