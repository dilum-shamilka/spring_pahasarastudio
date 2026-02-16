package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

    private Long id;
    private String itemName;
    private Integer quantity;
    private String unit;
    private Integer reorderLevel;
    private String status;
}