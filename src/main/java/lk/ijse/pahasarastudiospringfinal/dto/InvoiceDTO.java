package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDate date;
    private double totalAmount;
    private double paidAmount; // Added this field
    private String paymentStatus;
    private Long bookingId;
}