package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import lk.ijse.pahasarastudiospringfinal.repo.BookingRepo;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;

    public BookingServiceImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream().map(b -> {
            BookingDTO dto = new BookingDTO();
            dto.setId(b.getId());
            dto.setDateTime(b.getDateTime());
            dto.setStatus(b.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createBooking(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setDateTime(dto.getDateTime());
        booking.setStatus("PENDING");
        bookingRepo.save(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepo.deleteById(id);
    }
}