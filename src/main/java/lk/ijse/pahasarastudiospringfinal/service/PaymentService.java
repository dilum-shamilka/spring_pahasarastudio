package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.PaymentDTO;
import java.util.List;

public interface PaymentService {
    void processPayment(PaymentDTO paymentDTO);
    List<PaymentDTO> getPaymentsByInvoice(Long invoiceId);
}