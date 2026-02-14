package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioServiceDTO {
    private Long id;
    private String serviceName;
    private String description;
    private Double price; // Wrapper class to allow null checks
    private String status;
}