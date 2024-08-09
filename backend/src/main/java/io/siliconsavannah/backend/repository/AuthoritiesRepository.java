package io.siliconsavannah.backend.repository;

import io.siliconsavannah.backend.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authorities,Integer> {
}
