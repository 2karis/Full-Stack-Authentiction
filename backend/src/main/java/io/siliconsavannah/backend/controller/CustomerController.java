package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.ContentDto;
import io.siliconsavannah.backend.dto.CustomerDto;
import io.siliconsavannah.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto contentDto) {
        return ResponseEntity.ok(customerService.createCustomer(contentDto));
    }
}
