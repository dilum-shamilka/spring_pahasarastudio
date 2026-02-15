package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    boolean existsByBookingDateAndLocation(LocalDate bookingDate, String location);

    List<Booking> findByBookingDate(LocalDate bookingDate);

    List<Booking> findByClient_Email(String email);

    List<Booking> findByStatus(String status);
}
