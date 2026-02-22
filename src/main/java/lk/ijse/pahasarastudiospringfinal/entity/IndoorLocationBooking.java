package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "indoor_location_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndoorLocationBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName; // The Studio/Room name

    @Enumerated(EnumType.STRING)
    private ShootType shootType;

    private String customerName;

    private String clientEmail; // New Field Added

    private String contactNumber;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BigDecimal price;

    private String status;
}