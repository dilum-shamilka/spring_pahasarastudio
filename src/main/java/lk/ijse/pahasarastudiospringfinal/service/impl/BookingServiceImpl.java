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
        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        if (clientOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Optional<StudioServiceEntity> serviceOpt = serviceRepo.findById(dto.getServiceId());
        if (serviceOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        if (bookingRepo.existsByBookingDateAndLocation(dto.getBookingDate(), dto.getLocation()))
            return VarList.RSP_DUPLICATED;

        Booking booking = mapper.toBookingEntity(dto, clientOpt.get());
        booking.setService(serviceOpt.get());
        bookingRepo.save(booking);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateBooking(BookingDTO dto) {
        if (dto.getId() == null || !bookingRepo.existsById(dto.getId()))
            return VarList.RSP_NO_DATA_FOUND;

        Optional<Client> clientOpt = clientRepo.findByEmail(dto.getClientEmail());
        Optional<StudioServiceEntity> serviceOpt = serviceRepo.findById(dto.getServiceId());
        if (clientOpt.isEmpty() || serviceOpt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        Booking booking = mapper.toBookingEntity(dto, clientOpt.get());
        booking.setService(serviceOpt.get());
        bookingRepo.save(booking);
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
        return mapper.mapList(bookingRepo.findAll(), mapper::toBookingDTO);
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
        Optional<Booking> opt = bookingRepo.findById(id);
        if (opt.isEmpty()) return VarList.RSP_NO_DATA_FOUND;
        Booking booking = opt.get();
        booking.setStatus(status);
        bookingRepo.save(booking);
        return VarList.RSP_SUCCESS;
    }
}
