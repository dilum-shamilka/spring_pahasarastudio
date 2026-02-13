package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "studio_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private double price;
    private String description;
}