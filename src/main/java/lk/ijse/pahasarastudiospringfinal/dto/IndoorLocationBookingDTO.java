package lk.ijse.pahasarastudiospringfinal.dto;

import lk.ijse.pahasarastudiospringfinal.entity.ShootType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndoorLocationBookingDTO {

    private Long id;
    private String locationName;
    private ShootType shootType;
    private String customerName;
    private String contactNumber;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal price;
    private String status;
}
