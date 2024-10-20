package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.PaymentDto;
import io.siliconsavannah.backend.model.Invoice;
import io.siliconsavannah.backend.model.Payment;
import io.siliconsavannah.backend.repository.InvoiceRepository;
import io.siliconsavannah.backend.repository.PaymentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private InvoiceService invoiceService;
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    @SneakyThrows
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new Exception("Payment not found"));
    }

    @Transactional
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    public PaymentDto processPayment(PaymentDto paymentDto) {
        Invoice invoice = invoiceService.getInvoiceById(paymentDto.invoiceId());

        Payment payment = Payment.builder()
                .paymentDate(LocalDate.now())
                .amount(paymentDto.amount())
                .type(paymentDto.type())
                .details(paymentDto.details())
                .invoice(invoice)
                .build();

        invoice.setTotalAmount(invoice.getTotalAmount().subtract(paymentDto.amount()));
        invoiceService.saveInvoice(invoice);

        Payment paymentSaved = paymentRepository.save(payment);
        return PaymentDto.builder()
                .amount(paymentSaved.getAmount())
                .type(paymentSaved.getType())
                .details(paymentSaved.getDetails())
                .build();
    }

}
