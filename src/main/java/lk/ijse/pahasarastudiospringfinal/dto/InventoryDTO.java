package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private String itemName;
    private Integer quantity;
    private String unit; // e.g., "Rolls", "Pieces", "Packs"
    private Integer reorderLevel;
}