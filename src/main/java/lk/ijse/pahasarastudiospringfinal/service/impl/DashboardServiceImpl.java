package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.DashboardStatsDTO;
import lk.ijse.pahasarastudiospringfinal.repo.*;
import lk.ijse.pahasarastudiospringfinal.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private PaymentRepo paymentRepo; // Added for actual revenue calculation

    @Autowired
    private InventoryRepo inventoryRepo; // For low stock alerts

    @Override
    public DashboardStatsDTO getStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // ✅ Aggregating actual counts from Repositories
        stats.setTotalClients((int) clientRepo.count());
        stats.setTotalBookings((int) bookingRepo.count());
        stats.setPendingInvoices((int) invoiceRepo.countByStatus("UNPAID"));

        // ✅ Real-time revenue calculation (instead of hardcoded values)
        stats.setTotalRevenue(calculateTotalRevenue());

        return stats;
    }

    private double calculateTotalRevenue() {
        // Safe calculation of all payments recorded in the system
        return paymentRepo.findAll().stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();
    }

    @Override
    public int getActiveBookingsCount() {
        return (int) bookingRepo.countByStatus("CONFIRMED");
    }

    @Override
    public double getMonthlyRevenue() {
        return 0;
    }

    @Override
    public int getPendingInvoicesCount() {
        return 0;
    }

    @Override
    public int getLowStockItemCount() {
        // Example: logic to find items where quantity is below 5
        return (int) inventoryRepo.findAll().stream()
                .filter(item -> item.getQuantity() < 5)
                .count();
    }

    @Override
    public double getRevenueGrowthRate() {
        return 0;
    }

    @Override
    public int getNewClientsThisMonth() {
        return 0;
    }
}