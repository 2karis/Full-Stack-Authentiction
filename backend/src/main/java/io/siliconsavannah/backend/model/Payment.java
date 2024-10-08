package io.siliconsavannah.backend.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String type; // e.g., Credit Card, Bank Transfer
    private String details;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;



}
