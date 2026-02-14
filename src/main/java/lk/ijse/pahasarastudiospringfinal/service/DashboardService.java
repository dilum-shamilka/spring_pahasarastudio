package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.DashboardStatsDTO;

public interface DashboardService {

    /**
     * Aggregates data from all modules (Booking, Client, Invoice, etc.)
     * for a single-call dashboard initialization.
     */
    DashboardStatsDTO getStats();

    // --- Metrics for Summary Tiles ---

    int getActiveBookingsCount();

    double getMonthlyRevenue();

    int getPendingInvoicesCount();

    int getLowStockItemCount();

    // --- Performance Metrics ---

    /**
     * Useful for showing growth trends on the dashboard.
     * Returns the percentage increase in revenue compared to last month.
     */
    double getRevenueGrowthRate();

    /**
     * Useful for the "Recent Activity" section.
     */
    int getNewClientsThisMonth();
}