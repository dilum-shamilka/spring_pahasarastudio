package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();
    InvoiceDTO getById(Long id);
}