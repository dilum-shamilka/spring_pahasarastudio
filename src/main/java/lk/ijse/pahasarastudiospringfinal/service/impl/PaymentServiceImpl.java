package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.PaymentDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Payment;
import lk.ijse.pahasarastudiospringfinal.repo.PaymentRepo;
import lk.ijse.pahasarastudiospringfinal.service.PaymentService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService { // Corrected Name

    private final PaymentRepo paymentRepo;

    public PaymentServiceImpl(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void processPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTransactionTime(LocalDateTime.now());
        paymentRepo.save(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepo.findAll().stream()
                .filter(p -> p.getInvoice() != null && p.getInvoice().getId().equals(invoiceId))
                .map(p -> {
                    PaymentDTO dto = new PaymentDTO();
                    dto.setId(p.getId());
                    dto.setAmount(p.getAmount());
                    dto.setPaymentMethod(p.getPaymentMethod());
                    return dto;
                }).collect(Collectors.toList());
    }
}