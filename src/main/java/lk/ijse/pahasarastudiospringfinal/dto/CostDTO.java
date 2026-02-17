package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostDTO {
    private Long costId;
    private Double staffSalary;
    private Double equipmentCost;
    private Double monthlyShootsCost;
    private Double vehicleOilCost;
    private Double inventoryCost;
    private Double editCost;
    private Double printCost;
    private String date;
    private String description;
}