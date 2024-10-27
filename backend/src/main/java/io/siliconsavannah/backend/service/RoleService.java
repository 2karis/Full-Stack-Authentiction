package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.enums.Role;
import io.siliconsavannah.backend.model.Authorities;
import io.siliconsavannah.backend.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    public void createAuthority(){
        Authorities authority = Authorities.builder()
                .authority(Role.ROLE_SUPER_USER.name())
                .build();
        Authorities authority2 = Authorities.builder()
                .authority(Role.ROLE_USER.name())
                .build();
        Authorities authority3 = Authorities.builder()
                .authority(Role.ROLE_ADMIN.name())
                .build();

        authoritiesRepository.save(authority);
        authoritiesRepository.save(authority2);
        authoritiesRepository.save(authority3);
    }
}
