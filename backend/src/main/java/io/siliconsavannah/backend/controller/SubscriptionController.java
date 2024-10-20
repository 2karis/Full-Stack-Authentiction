package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.SubscribeDto;
import io.siliconsavannah.backend.dto.UserDto;
import io.siliconsavannah.backend.model.Subscription;
import io.siliconsavannah.backend.service.SubscriptionService;
import io.siliconsavannah.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    public SubscriptionService subscriptionService;
    @GetMapping("/subscribe")
    public ResponseEntity<Object> getAllUsers(@RequestParam SubscribeDto subscribeDto){
        return new ResponseEntity<>(subscriptionService.createSubscription(subscribeDto), HttpStatus.OK);
    }

}
