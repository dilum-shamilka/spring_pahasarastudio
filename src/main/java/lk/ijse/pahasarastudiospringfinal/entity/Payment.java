package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String paymentMethod; // CASH, CARD
    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}