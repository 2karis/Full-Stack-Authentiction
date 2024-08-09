package io.siliconsavannah.backend.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Column(columnDefinition="boolean default 1")
    private boolean accountNonExpired;
    @Column(columnDefinition="boolean default 1")
    private boolean accountNonLocked;
    @Column(columnDefinition="boolean default 1")
    private boolean credentialsNonExpired;
    @Column(columnDefinition="boolean default 1")
    private boolean enabled;
    private String role;
//    @OneToMany(fetch = FetchType.EAGER)
//    private Set<Authorities> authorities;
}
