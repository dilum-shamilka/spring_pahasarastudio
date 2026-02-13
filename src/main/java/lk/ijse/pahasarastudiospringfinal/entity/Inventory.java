package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int quantity;
    private String unit; // e.g., "Rolls", "Box"
    private int reorderLevel;
}