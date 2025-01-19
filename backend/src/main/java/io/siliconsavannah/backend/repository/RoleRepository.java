package io.siliconsavannah.backend.repository;

import io.siliconsavannah.backend.enums.RoleName;
import io.siliconsavannah.backend.model.Authority;
import io.siliconsavannah.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
