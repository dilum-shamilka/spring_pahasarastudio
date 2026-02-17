package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String code;    // "00" success, "01" error
    private String message;
    private Object content; // UserDTO, role, etc.
}
