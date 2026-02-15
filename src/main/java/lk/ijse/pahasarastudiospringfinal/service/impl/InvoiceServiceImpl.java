package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.InvoiceService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveInvoice(InvoiceDTO invoiceDTO) {
        // 1. Check if Invoice Number already exists
        if (invoiceRepo.existsByInvoiceNumber(invoiceDTO.getInvoiceNumber())) {
            return VarList.RSP_DUPLICATED;
        }

        // 2. Check if this Booking already has an invoice (One-to-One check)
        if (invoiceRepo.existsByBookingId(invoiceDTO.getBookingId())) {
            return VarList.RSP_DUPLICATED;
        }

        Optional<Booking> booking = bookingRepo.findById(invoiceDTO.getBookingId());
        if (booking.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);
        invoice.setBooking(booking.get());
        invoice.setStatus(invoiceDTO.getPaymentStatus() != null ? invoiceDTO.getPaymentStatus() : "UNPAID");

        invoiceRepo.save(invoice);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateInvoice(InvoiceDTO invoiceDTO) {
        // 1. Check if the Invoice exists
        Optional<Invoice> existingInvoiceOpt = invoiceRepo.findById(invoiceDTO.getId());
        if (existingInvoiceOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Invoice existingInvoice = existingInvoiceOpt.get();

        // 2. Check if the new Invoice Number is taken by someone ELSE
        Invoice invoiceWithSameNumber = invoiceRepo.findByInvoiceNumber(invoiceDTO.getInvoiceNumber()).orElse(null);
        if (invoiceWithSameNumber != null && !invoiceWithSameNumber.getId().equals(invoiceDTO.getId())) {
            return VarList.RSP_DUPLICATED;
        }

        // 3. Check if the new Booking ID is valid
        Optional<Booking> bookingOpt = bookingRepo.findById(invoiceDTO.getBookingId());
        if (bookingOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        // 4. Update the managed entity fields
        existingInvoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        existingInvoice.setDate(invoiceDTO.getDate());
        existingInvoice.setTotalAmount(invoiceDTO.getTotalAmount());
        existingInvoice.setStatus(invoiceDTO.getPaymentStatus());
        existingInvoice.setBooking(bookingOpt.get());

        invoiceRepo.save(existingInvoice);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepo.findAll().stream().map(invoice -> {
            InvoiceDTO dto = new InvoiceDTO();
            dto.setId(invoice.getId());
            dto.setInvoiceNumber(invoice.getInvoiceNumber());
            dto.setDate(invoice.getDate());
            dto.setTotalAmount(invoice.getTotalAmount());
            dto.setPaymentStatus(invoice.getStatus());
            if (invoice.getBooking() != null) {
                dto.setBookingId(invoice.getBooking().getId());
            }
            return dto;
        }).collect(Collectors.toList());
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
        return invoiceRepo.findById(id).map(invoice -> {
            InvoiceDTO dto = new InvoiceDTO();
            dto.setId(invoice.getId());
            dto.setInvoiceNumber(invoice.getInvoiceNumber());
            dto.setDate(invoice.getDate());
            dto.setTotalAmount(invoice.getTotalAmount());
            dto.setPaymentStatus(invoice.getStatus());
            dto.setBookingId(invoice.getBooking().getId());
            return dto;
        }).orElse(null);
    }

    @Override
    public InvoiceDTO getInvoiceByNumber(String n) {
        return invoiceRepo.findByInvoiceNumber(n).map(invoice -> {
            InvoiceDTO dto = modelMapper.map(invoice, InvoiceDTO.class);
            dto.setPaymentStatus(invoice.getStatus());
            dto.setBookingId(invoice.getBooking().getId());
            return dto;
        }).orElse(null);
    }

    @Override public int getTotalInvoiceCount() { return (int) invoiceRepo.count(); }
    @Override public List<InvoiceDTO> getInvoicesByStatus(String s) { return null; }
    @Override public InvoiceDTO getInvoiceByBookingId(Long id) { return null; }
    @Override public boolean updateInvoiceStatus(Long id, String s) { return false; }
}