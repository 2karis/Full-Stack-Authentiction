package io.siliconsavannah.backend.repo;

import io.siliconsavannah.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

}
