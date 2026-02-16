package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerDTO {
    private Long id;
    private String name;
    private String email;
    private Double salary;
    private String status;
}
