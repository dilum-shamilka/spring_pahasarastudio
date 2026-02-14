package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.entity.Invoice;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.InvoiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.InvoiceService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private BookingRepo bookingRepo; // Added to fetch Booking entities

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveInvoice(InvoiceDTO invoiceDTO) {
        // 1. Check for duplicate invoice numbers
        if (invoiceRepo.existsByInvoiceNumber(invoiceDTO.getInvoiceNumber())) {
            return VarList.RSP_DUPLICATED;
        }

        // 2. Fetch the actual Booking object
        Optional<Booking> booking = bookingRepo.findById(invoiceDTO.getBookingId());
        if (booking.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        // 3. Map DTO to Entity
        Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);

        // 4. Manually set the Booking (ModelMapper can't fetch from DB)
        invoice.setBooking(booking.get());

        if (invoice.getStatus() == null) {
            invoice.setStatus("UNPAID");
        }

        invoiceRepo.save(invoice);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateInvoice(InvoiceDTO invoiceDTO) {
        // 1. Verify if the Invoice exists
        if (invoiceRepo.existsById(invoiceDTO.getId())) {

            // 2. Fetch the Booking object for the update
            Optional<Booking> booking = bookingRepo.findById(invoiceDTO.getBookingId());
            if (booking.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

            // 3. Map and set the ID specifically to trigger an UPDATE instead of INSERT
            Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);
            invoice.setBooking(booking.get());

            invoiceRepo.save(invoice);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoiceList = invoiceRepo.findAll();
        return modelMapper.map(invoiceList, new TypeToken<List<InvoiceDTO>>() {}.getType());
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepo.findById(id)
                .map(value -> modelMapper.map(value, InvoiceDTO.class))
                .orElse(null);
    }

    @Override
    public InvoiceDTO getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepo.findByInvoiceNumber(invoiceNumber)
                .map(value -> modelMapper.map(value, InvoiceDTO.class))
                .orElse(null);
    }

    @Override
    public boolean updateInvoiceStatus(Long id, String status) {
        Optional<Invoice> optionalInvoice = invoiceRepo.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setStatus(status);
            invoiceRepo.save(invoice);
            return true;
        }
        return false;
    }

    @Override
    public List<InvoiceDTO> getInvoicesByStatus(String status) {
        List<Invoice> invoiceList = invoiceRepo.findByStatus(status);
        return modelMapper.map(invoiceList, new TypeToken<List<InvoiceDTO>>() {}.getType());
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
    public int getTotalInvoiceCount() {
        return (int) invoiceRepo.count();
    }

    @Override
    public InvoiceDTO getInvoiceByBookingId(Long bookingId) {
        return invoiceRepo.findByBookingId(bookingId)
                .map(value -> modelMapper.map(value, InvoiceDTO.class))
                .orElse(null);
    }
}