package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId; // Changed from 'id' to 'userId' to match Service logic
    private String username;
    private String password;
    private String email;
    private String role;
}