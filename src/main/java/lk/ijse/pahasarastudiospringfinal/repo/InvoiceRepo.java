package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    boolean existsByInvoiceNumber(String invoiceNumber);

    // Check if a booking ID already exists in the invoices table
    boolean existsByBookingId(Long bookingId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}