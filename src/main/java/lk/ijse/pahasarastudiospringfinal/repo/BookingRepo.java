package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    // ✅ FIXED: Changed DateTime to BookingDate
    boolean existsByBookingDate(LocalDate bookingDate);

    // ✅ FIXED: Changed DateTime to BookingDate
    List<Booking> findAllByBookingDate(LocalDate date);

    // ✅ Standardized status search
    List<Booking> findAllByStatus(String status);

    long countByStatus(String status);

    boolean existsByBookingDateAndLocation(LocalDate date, String location);

    // ✅ Note: This requires a 'client' property in Booking and 'email' in Client
    List<Booking> findByClientEmail(String email);

    // ✅ Use this if you want to find bookings for a specific day
    List<Booking> findByBookingDate(LocalDate date);
}