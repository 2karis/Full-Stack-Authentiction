package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.SubscribeDto;
import io.siliconsavannah.backend.model.Customer;
import io.siliconsavannah.backend.model.Subscription;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.SubscriptionRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

// SubscriptionService.java
@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;

    public Subscription createSubscription(SubscribeDto subscribeDto) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(startDate);
        User user = userRepository.findById(subscribeDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription subscription = Subscription.builder()
                .user(user)
                .monthlyFee(subscribeDto.monthlyFee())
                .startDate(LocalDate.now())
                .build();

        // Optionally, calculate and set the end date based on business rules
        // Save the subscription to the database
        if(hasOverlappingSubscriptions(subscribeDto.userId(),startDate,endDate)){
            throw new RuntimeException("Overlapping Subscriptions");

        }
        return subscriptionRepository.save(subscription);

    }

    public LocalDate calculateEndDate(LocalDate startDate) {
        // Assuming a monthly subscription
        return startDate.plusYears(1); // Add one month to the start date
    }
    public boolean hasOverlappingSubscriptions(int userId, LocalDate newStartDate, LocalDate newEndDate) {
        int count = subscriptionRepository.overlappingSubscriptions(userId,newStartDate,newEndDate);
        return count > 0;
    }
}
