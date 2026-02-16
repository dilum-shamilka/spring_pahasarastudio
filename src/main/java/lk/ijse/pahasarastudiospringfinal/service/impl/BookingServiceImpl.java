package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String saveBooking(BookingDTO dto) {
        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        if (clientOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        if (bookingRepo.existsByBookingDateAndLocation(dto.getBookingDate(), dto.getLocation()))
            return VarList.RSP_DUPLICATED;

        bookingRepo.save(mapper.toBookingEntity(dto, clientOpt.get()));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateBooking(BookingDTO dto) {
        if (dto.getId() == null || !bookingRepo.existsById(dto.getId()))
            return VarList.RSP_NO_DATA_FOUND;

        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        if (clientOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        bookingRepo.save(mapper.toBookingEntity(dto, clientOpt.get()));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteBooking(Long id) {
        if (!bookingRepo.existsById(id)) return VarList.RSP_NO_DATA_FOUND;
        bookingRepo.deleteById(id);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingRepo.findById(id).map(mapper::toBookingDTO).orElse(null);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream()
                .map(mapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByDate(LocalDate date) {
        return bookingRepo.findByBookingDate(date).stream()
                .map(mapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByClientEmail(String email) {
        return bookingRepo.findByClient_Email(email).stream()
                .map(mapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByStatus(String status) {
        return bookingRepo.findByStatus(status).stream()
                .map(mapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalBookingCount() {
        return (int) bookingRepo.count();
    }

    @Override
    public void cancelBooking(Long id) {
        if (bookingRepo.existsById(id)) bookingRepo.deleteById(id);
    }

    @Override
    public String updateBookingStatus(Long id, String status) {
        Optional<Booking> opt = bookingRepo.findById(id);
        if (opt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Booking booking = opt.get();
        booking.setStatus(status);
        bookingRepo.save(booking);
        return VarList.RSP_SUCCESS;
    }
}