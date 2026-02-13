package lk.ijse.pahasarastudiospringfinal.service.serviceimpl;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.InvoiceService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepo invoiceRepo;

    public InvoiceServiceImpl(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepo.findAll().stream().map(i -> {
            InvoiceDTO dto = new InvoiceDTO();
            dto.setId(i.getId());
            dto.setInvoiceNumber(i.getInvoiceNumber());
            dto.setTotalAmount(i.getTotalAmount());
            return dto;
        }).toList();
    }

    @Override
    public InvoiceDTO getById(Long id) {
        var i = invoiceRepo.findById(id).orElseThrow();
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(i.getId());
        dto.setInvoiceNumber(i.getInvoiceNumber());
        return dto;
    }
}