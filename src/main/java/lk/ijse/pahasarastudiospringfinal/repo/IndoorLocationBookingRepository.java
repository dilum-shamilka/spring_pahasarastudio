package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.IndoorLocationBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IndoorLocationBookingRepository extends JpaRepository<IndoorLocationBooking, Long> {

    List<IndoorLocationBooking> findByBookingDate(LocalDate bookingDate);

    List<IndoorLocationBooking>
    findByBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            LocalDate bookingDate,
            LocalTime startTime,
            LocalTime endTime
    );
}
