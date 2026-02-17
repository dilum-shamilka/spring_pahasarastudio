package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoFrame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String frameName;
    private String material; // Wood, Plastic, Metal, Glass
    private String size;     // e.g. 4x6, 8x10, 12x18
    private double unitPrice;
    private int qtyOnHand;
}
