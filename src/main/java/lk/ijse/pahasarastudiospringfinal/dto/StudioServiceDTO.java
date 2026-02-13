package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;

@Data
public class StudioServiceDTO {
    private Long id;
    private String serviceName;
    private double price;
    private String description;
}