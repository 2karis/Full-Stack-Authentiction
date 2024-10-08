package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.PaymentDto;
import io.siliconsavannah.backend.model.Payment;
import io.siliconsavannah.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public PaymentDto processPayment(@RequestParam PaymentDto paymentDto) {
        return paymentService.processPayment(paymentDto);
    }
}
