package io.siliconsavannah.backend.dto;

import io.siliconsavannah.backend.enums.RoleName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserSeedDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleName role ;
    private LocalDateTime verifiedAt;
    private String profile;

    public UserSeedDto(String firstname, String lastname, String email, String password, RoleName role, LocalDateTime verifiedAt, String profile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verifiedAt = verifiedAt;
        this.profile = profile;
    }
}
