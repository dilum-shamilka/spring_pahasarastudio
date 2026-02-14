package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import java.util.List;

public interface InvoiceService {

    // ✅ Saves a new invoice and returns VarList code (00, 06, etc.)
    String saveInvoice(@Valid InvoiceDTO invoiceDTO);

    // ✅ Standardized update for the PutMapping in InvoiceController
    String updateInvoice(InvoiceDTO invoiceDTO);

    // ✅ Deletes an invoice and returns VarList code
    String deleteInvoice(Long id);

    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO getInvoiceByNumber(String invoiceNumber);

    // Functional method for the Payment processing logic
    boolean updateInvoiceStatus(Long id, String status);

    List<InvoiceDTO> getInvoicesByStatus(String status);

    int getTotalInvoiceCount();

    // To link invoices to specific bookings
    InvoiceDTO getInvoiceByBookingId(Long bookingId);
}