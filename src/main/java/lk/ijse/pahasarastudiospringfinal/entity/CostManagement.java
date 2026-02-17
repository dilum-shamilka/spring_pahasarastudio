package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long costId;

    private Double staffSalary; // Photographer/Videographer
    private Double equipmentCost;
    private Double monthlyShootsCost;
    private Double vehicleOilCost;
    private Double inventoryCost;
    private Double editCost;
    private Double printCost;
    private String date;
    private String description;
}