package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String status;
    private Long clientId;
    private String clientName;
    private Long serviceId;
}