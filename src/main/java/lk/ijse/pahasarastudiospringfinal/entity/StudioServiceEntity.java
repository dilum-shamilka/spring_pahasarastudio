package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false, unique = true) // ✅ FIXED MAPPING
    private String serviceName;

    @Column(nullable = false)
    private double price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String status = "ACTIVE";
}