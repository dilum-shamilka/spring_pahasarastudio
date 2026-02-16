package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    private String serialNumber;

    @Column(nullable = false)
    private String status = "AVAILABLE";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "equipment_images", joinColumns = @JoinColumn(name = "equipment_id"))
    private List<String> equipmentImages;
}