package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long invoiceId;
    private double amount;
    private String paymentMethod; // Cash, Card, Bank Transfer
    private LocalDateTime transactionTime;
}