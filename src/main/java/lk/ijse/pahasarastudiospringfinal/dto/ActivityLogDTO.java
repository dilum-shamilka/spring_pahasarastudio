package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityLogDTO {
    private Long id;
    private String username;
    private String action; // e.g., "UPDATED_BOOKING"
    private LocalDateTime timestamp;
    private String details;
}