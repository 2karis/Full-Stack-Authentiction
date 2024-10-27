package io.siliconsavannah.backend.repository;

import io.siliconsavannah.backend.model.Content;
import io.siliconsavannah.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Integer> {
    List<Content> findAllByUser(User user);
}
