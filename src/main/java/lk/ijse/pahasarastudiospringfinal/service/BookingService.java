package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    String saveBooking(BookingDTO dto);
    String updateBooking(BookingDTO dto);
    String deleteBooking(Long id);
    BookingDTO getBookingById(Long id);
    List<BookingDTO> getAllBookings();
    List<BookingDTO> getBookingsByDate(LocalDate date);
    List<BookingDTO> getBookingsByClientEmail(String email);
    List<BookingDTO> getBookingsByStatus(String status);
    int getTotalBookingCount();
    void cancelBooking(Long id);
    String updateBookingStatus(Long id, String status);
}
