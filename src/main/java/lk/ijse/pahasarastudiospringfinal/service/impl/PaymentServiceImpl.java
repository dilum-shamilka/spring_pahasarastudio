package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.PaymentDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import lk.ijse.pahasarastudiospringfinal.entity.Payment;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.repo.PaymentRepo;
import lk.ijse.pahasarastudiospringfinal.service.PaymentService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String processPayment(PaymentDTO paymentDTO) {
        Optional<Invoice> invoiceOpt = invoiceRepo.findById(paymentDTO.getInvoiceId());
        if (invoiceOpt.isPresent()) {
            if (paymentDTO.getTransactionTime() == null) paymentDTO.setTransactionTime(LocalDateTime.now());
            // Save as New (ID will be null in DTO usually)
            paymentRepo.save(mapper.toPaymentEntity(paymentDTO, invoiceOpt.get()));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String updatePayment(PaymentDTO paymentDTO) {
        // 1. Verify Payment ID exists
        if (paymentDTO.getId() == null || !paymentRepo.existsById(paymentDTO.getId())) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // 2. Verify Invoice ID exists
        Optional<Invoice> invoiceOpt = invoiceRepo.findById(paymentDTO.getInvoiceId());
        if (invoiceOpt.isPresent()) {
            // 3. MappingUtil will attach the ID, triggering an UPDATE SQL
            paymentRepo.save(mapper.toPaymentEntity(paymentDTO, invoiceOpt.get()));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deletePayment(Long id) {
        if (paymentRepo.existsById(id)) {
            paymentRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepo.findAll().stream()
                .map(mapper::toPaymentDTO)
                .collect(Collectors.toList());
    }
}