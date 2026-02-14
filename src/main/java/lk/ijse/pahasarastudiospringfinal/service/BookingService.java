package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    // ✅ Saves a new booking and returns VarList code (00=Success, 06=Conflict)
    String saveBooking(@Valid BookingDTO bookingDTO);

    // ✅ Standardized update for the PutMapping in BookingController
    String updateBooking(BookingDTO bookingDTO);

    // ✅ Matches @DeleteMapping("/delete/{bookingID}")
    String deleteBooking(int id);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(int id);

    // --- Studio Specific Logic ---

    // Allows quick status changes (e.g., CONFIRMED, COMPLETED, CANCELLED)
    String updateBookingStatus(int id, String status);

    BookingDTO getBookingById(Long id);

    String updateBookingStatus(Long id, String status);

    String updateBooking(Long id, BookingDTO bookingDTO);

    String deleteBooking(Long id);

    // Crucial for the Studio Schedule/Calendar view
    List<BookingDTO> getBookingsByDate(LocalDate date);

    List<BookingDTO> getBookingsByClientEmail(String email);

    List<BookingDTO> getBookingsByStatus(String status);

    // Support for Dashboard metrics
    int getTotalBookingCount();

    // Standard interface bridges
    void createBooking(BookingDTO dto);

    void cancelBooking(Long id);
}