package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {

    String saveInvoice(@Valid InvoiceDTO invoiceDTO);

    String updateInvoice(InvoiceDTO invoiceDTO);

    String deleteInvoice(Long id);

    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO getInvoiceByNumber(String invoiceNumber);

    boolean updateInvoiceStatus(Long id, String status);

    List<InvoiceDTO> getInvoicesByStatus(String status);

    int getTotalInvoiceCount();

    InvoiceDTO getInvoiceByBookingId(Long bookingId);
}
