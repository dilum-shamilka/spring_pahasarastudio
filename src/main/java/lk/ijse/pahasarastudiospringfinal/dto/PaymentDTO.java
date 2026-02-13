package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Long invoiceId;
    private double amount;
    private String paymentMethod; // Cash, Card, Bank Transfer
    private LocalDateTime transactionTime;
}