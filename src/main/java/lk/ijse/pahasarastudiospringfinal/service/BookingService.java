package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import java.util.List;

public interface BookingService {
    List<BookingDTO> getAllBookings();
    void createBooking(BookingDTO bookingDTO);
    void cancelBooking(Long id);
}