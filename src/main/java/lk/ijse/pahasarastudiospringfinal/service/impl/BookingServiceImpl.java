package lk.ijse.pahasarastudiospringfinal.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.entity.StudioServiceEntity;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.repo.StudioServiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private StudioServiceRepo serviceRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String saveBooking(BookingDTO dto) {
        // Validate Client existence
        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        if (clientOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        // Validate Service existence
        Optional<StudioServiceEntity> serviceOpt = serviceRepo.findById(dto.getServiceId());
        if (serviceOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        // Check for double bookings at the same time and place
        if (bookingRepo.existsByBookingDateAndLocation(dto.getBookingDate(), dto.getLocation())) {
            return VarList.RSP_DUPLICATED;
        }

        Booking booking = mapper.toBookingEntity(dto, clientOpt.get());
        booking.setService(serviceOpt.get());

        // Ensure ID is null for a new save to prevent accidental updates
        booking.setId(null);

        bookingRepo.save(booking);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateBooking(BookingDTO dto) {
        // Check if the booking actually exists before updating
        if (dto.getId() == null || !bookingRepo.existsById(dto.getId())) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        Optional<StudioServiceEntity> serviceOpt = serviceRepo.findById(dto.getServiceId());

        if (clientOpt.isEmpty() || serviceOpt.isEmpty()) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // Map DTO to Entity
        Booking booking = mapper.toBookingEntity(dto, clientOpt.get());
        booking.setService(serviceOpt.get());

        // CRITICAL: Explicitly set the ID so Hibernate knows to UPDATE instead of INSERT
        booking.setId(dto.getId());

        bookingRepo.save(booking);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteBooking(Long id) {
        if (!bookingRepo.existsById(id)) {
            return VarList.RSP_NO_DATA_FOUND;
        }
        bookingRepo.deleteById(id);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(mapper::toBookingDTO)
                .orElse(null);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        // Fetches all bookings and converts them to a List of DTOs for the UI table
        List<Booking> allBookings = bookingRepo.findAll();
        return mapper.mapList(allBookings, mapper::toBookingDTO);
    }

    @Override
    public List<BookingDTO> getBookingsByDate(LocalDate date) {
        return mapper.mapList(bookingRepo.findByBookingDate(date), mapper::toBookingDTO);
    }

    @Override
    public List<BookingDTO> getBookingsByClientEmail(String email) {
        return mapper.mapList(bookingRepo.findByClient_Email(email), mapper::toBookingDTO);
    }

    @Override
    public List<BookingDTO> getBookingsByStatus(String status) {
        return mapper.mapList(bookingRepo.findByStatus(status), mapper::toBookingDTO);
    }

    @Override
    public int getTotalBookingCount() {
        return (int) bookingRepo.count();
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepo.findById(id).ifPresent(b -> {
            b.setStatus("CANCELLED");
            bookingRepo.save(b);
        });
    }

    @Override
    public String updateBookingStatus(Long id, String status) {
        return bookingRepo.findById(id).map(booking -> {
            booking.setStatus(status);
            bookingRepo.save(booking);
            return VarList.RSP_SUCCESS;
        }).orElse(VarList.RSP_NO_DATA_FOUND);
    }
}