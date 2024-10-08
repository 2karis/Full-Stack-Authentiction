package io.siliconsavannah.backend.repository;

import io.siliconsavannah.backend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s WHERE s.startDate <= :today AND (s.endDate IS NULL OR s.endDate >= :today)")
    List<Subscription> findActiveSubscriptions(LocalDate today);
}