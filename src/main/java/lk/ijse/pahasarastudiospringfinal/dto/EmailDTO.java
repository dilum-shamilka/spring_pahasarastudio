package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private Long id;
    private String toEmail;
    private String subject;
    private String message;
}
