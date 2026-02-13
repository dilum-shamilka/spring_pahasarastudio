package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "studio_services")
@Data
public abstract class StudioService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName; // e.g. "Wedding Photography"
    private double price;
    private String description;

    public abstract List<StudioServiceDTO> getAllServices();

    public abstract void saveService(StudioServiceDTO dto);
}