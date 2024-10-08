package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.model.BillingItem;
import io.siliconsavannah.backend.model.Invoice;
import io.siliconsavannah.backend.repository.BillingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class BillingItemService {
    @Autowired
    private BillingItemRepository billingItemRepository;

    @Transactional
    public void saveBillingItem(Invoice invoice,String description, BigDecimal monthlyFee){
        BillingItem monthlyFeeBill = BillingItem.builder()
                .invoice(invoice)
                .description(description)
                .quantity(1)
                .unitPrice(monthlyFee)
                .build();

        billingItemRepository.save(monthlyFeeBill);
    }
}
