package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "albums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String albumName;
    private String albumType;
    private Double price;
    private String status;

    // IMPORTANT → Use EAGER to avoid lazy loading issue in DTO
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
