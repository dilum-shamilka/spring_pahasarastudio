package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.InvoiceService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired private InvoiceRepo invoiceRepo;
    @Autowired private BookingRepo bookingRepo;

    @Override
    public String saveInvoice(InvoiceDTO dto) {
        if (invoiceRepo.existsByInvoiceNumber(dto.getInvoiceNumber())) return VarList.RSP_DUPLICATED;

        Optional<Booking> booking = bookingRepo.findById(dto.getBookingId());
        if (booking.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setDate(dto.getDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setPaidAmount(dto.getPaidAmount()); // Map paidAmount
        invoice.setStatus(dto.getPaymentStatus());
        invoice.setBooking(booking.get());

        invoiceRepo.save(invoice);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepo.findAll().stream().map(inv -> new InvoiceDTO(
                inv.getId(),
                inv.getInvoiceNumber(),
                inv.getDate(),
                inv.getTotalAmount(),
                inv.getPaidAmount(), // Map paidAmount back to DTO
                inv.getStatus(),
                inv.getBooking().getId()
        )).collect(Collectors.toList());
    }

    @Override
    public String updateInvoice(InvoiceDTO dto) {
        Optional<Invoice> existing = invoiceRepo.findById(dto.getId());
        if (existing.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Optional<Booking> booking = bookingRepo.findById(dto.getBookingId());
        if (booking.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Invoice invoice = existing.get();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setDate(dto.getDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setPaidAmount(dto.getPaidAmount()); // Update paidAmount
        invoice.setStatus(dto.getPaymentStatus());
        invoice.setBooking(booking.get());

        invoiceRepo.save(invoice);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteInvoice(Long id) {
        if (invoiceRepo.existsById(id)) {
            invoiceRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepo.findById(id).map(inv -> new InvoiceDTO(
                inv.getId(), inv.getInvoiceNumber(), inv.getDate(),
                inv.getTotalAmount(), inv.getPaidAmount(), inv.getStatus(), inv.getBooking().getId()
        )).orElse(null);
    }

    @Override public int getTotalInvoiceCount() { return (int) invoiceRepo.count(); }
    @Override public InvoiceDTO getInvoiceByNumber(String n) { return null; }
    @Override public List<InvoiceDTO> getInvoicesByStatus(String s) { return null; }
    @Override public InvoiceDTO getInvoiceByBookingId(Long id) { return null; }
    @Override public boolean updateInvoiceStatus(Long id, String s) { return false; }
}