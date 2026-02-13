package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.DashboardStatsDTO;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final ClientRepo clientRepo;
    private final BookingRepo bookingRepo;
    private final InvoiceRepo invoiceRepo;

    public DashboardServiceImpl(ClientRepo clientRepo, BookingRepo bookingRepo, InvoiceRepo invoiceRepo) {
        this.clientRepo = clientRepo;
        this.bookingRepo = bookingRepo;
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public DashboardStatsDTO getStats() {
        return new DashboardStatsDTO(
                clientRepo.count(),
                bookingRepo.count(),
                50000.0, // This would usually be a sum query from InvoiceRepo
                invoiceRepo.count()
        );
    }
}