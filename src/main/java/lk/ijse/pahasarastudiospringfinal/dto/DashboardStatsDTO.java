package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {
    private long totalClients;
    private long totalBookings;
    private double monthlyRevenue;
    private long pendingInvoices;

    public void setTotalRevenue(double v) {

    }
}