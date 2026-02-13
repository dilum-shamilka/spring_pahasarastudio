package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalClients;
    private long totalBookings;
    private double monthlyRevenue;
    private long pendingInvoices;
}