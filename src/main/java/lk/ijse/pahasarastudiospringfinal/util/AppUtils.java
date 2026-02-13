package lk.ijse.pahasarastudiospringfinal.util;

import java.util.UUID;

public class AppUtils {

    // Generates a unique ID for Invoices or Bookings
    public static String generateId() {
        return UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }

    // Helper to check if a string is actually empty
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}