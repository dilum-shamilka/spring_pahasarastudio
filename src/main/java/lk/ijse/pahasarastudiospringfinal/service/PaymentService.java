package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.PaymentDTO;
import java.util.List;

public interface PaymentService {
    String processPayment(PaymentDTO paymentDTO); // Save
    String updatePayment(PaymentDTO paymentDTO);  // Update
    String deletePayment(Long id);                // Delete
    List<PaymentDTO> getAllPayments();           // GetAll
}