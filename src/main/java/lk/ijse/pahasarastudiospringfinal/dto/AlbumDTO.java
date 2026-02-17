package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private Long id;
    private String albumName;
    private String albumType;
    private Double price;
    private String status;

    private Long clientId;
    private String clientName;
}
