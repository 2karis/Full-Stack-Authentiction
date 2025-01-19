package io.siliconsavannah.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String password;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role ;
    private LocalDateTime verifiedAt;
    private String profile;
    private Long parentId;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList <GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : role.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
