package io.siliconsavannah.backend.repository;


import io.siliconsavannah.backend.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.dueDate < :today AND i.totalAmount > 0")
    List<Invoice> findPastDueInvoices(LocalDate today);
}
