package lk.ijse.pahasarastudiospringfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookingDate;
    private String location;
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_email", referencedColumnName = "email", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = true)
    private StudioServiceEntity service;
}