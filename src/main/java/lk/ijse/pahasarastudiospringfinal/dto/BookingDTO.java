package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private LocalDate bookingDate;
    private String location;
    private String status;

    // Client Info
    private String clientEmail;

    // Service Info
    private Long serviceId;
    private String serviceName;
}