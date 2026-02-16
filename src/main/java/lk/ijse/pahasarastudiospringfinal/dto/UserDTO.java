package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;      // Changed to Long to match entity
    private String username;
    private String password;
    private String email;
    private String role;
}
