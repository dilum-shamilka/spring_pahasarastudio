package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDate date;
    private double totalAmount;
    private String paymentStatus;
    private Long bookingId;
}