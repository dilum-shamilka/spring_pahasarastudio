package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {
    private Long id;
    private String itemName;
    private String category;    // Camera, Lens, Lighting
    private String serialNumber;
    private String status;      // AVAILABLE, IN_USE, REPAIRING
}