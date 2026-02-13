package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;

@Data
public class EquipmentDTO {
    private Long id;
    private String name;
    private String serialNumber;
    private boolean isAvailable;
}