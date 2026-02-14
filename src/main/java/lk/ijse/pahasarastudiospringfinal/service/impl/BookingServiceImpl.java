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
    public String saveBooking(BookingDTO bookingDTO) {
        // PRE-VALIDATION: Check if client exists to prevent transaction rollback
        Optional<Client> clientOpt = clientRepo.findByEmail(bookingDTO.getClientEmail());
        if (clientOpt.isEmpty()) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // Check for double booking (Optional business logic)
        if (bookingRepo.existsByBookingDateAndLocation(bookingDTO.getBookingDate(), bookingDTO.getLocation())) {
            return VarList.RSP_DUPLICATED;
        }

        bookingRepo.save(mapper.toBookingEntity(bookingDTO, clientOpt.get()));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateBooking(BookingDTO bookingDTO) {
        // 1. Check if Booking ID is valid
        if (bookingDTO.getId() == null || !bookingRepo.existsById(bookingDTO.getId())) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // 2. Validate Client existence
        Optional<Client> clientOpt = clientRepo.findByEmail(bookingDTO.getClientEmail());
        if (clientOpt.isEmpty()) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // 3. Save (JPA performs Update because ID is present in the Entity)
        bookingRepo.save(mapper.toBookingEntity(bookingDTO, clientOpt.get()));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteBooking(Long id) {
        if (bookingRepo.existsById(id)) {
            bookingRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<BookingDTO> getBookingsByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<BookingDTO> getBookingsByClientEmail(String email) {
        return List.of();
    }

    @Override
    public List<BookingDTO> getBookingsByStatus(String status) {
        return List.of();
    }

    @Override
    public int getTotalBookingCount() {
        return 0;
    }

    @Override
    public void createBooking(BookingDTO dto) {

    }

    @Override
    public void cancelBooking(Long id) {

    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream()
                .map(mapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    // Bridges for Interface Compatibility
    @Override public String deleteBooking(int id) { return deleteBooking((long) id); }
    @Override public BookingDTO getBookingById(int id) { return getBookingById((long) id); }

    @Override
    public String updateBookingStatus(int id, String status) {
        return "";
    }

    @Override public BookingDTO getBookingById(Long id) { return bookingRepo.findById(id).map(mapper::toBookingDTO).orElse(null); }

    @Override
    public String updateBookingStatus(Long id, String status) {
        return "";
    }

    @Override public String updateBooking(Long id, BookingDTO dto) { dto.setId(id); return updateBooking(dto); }
}