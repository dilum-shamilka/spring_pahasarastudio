package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {

    // ✅ Matches Entity field 'invoiceNumber'
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    boolean existsByInvoiceNumber(String invoiceNumber);

    // ✅ FIXED: Renamed from findByPaymentStatus to findByStatus
    List<Invoice> findByStatus(String status);

    // ✅ FIXED: Using the property 'id' inside the 'booking' object
    Optional<Invoice> findByBookingId(Long bookingId);

    // ✅ FIXED: Return type should be long for JPA counts
    long countByStatus(String status);
}