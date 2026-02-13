package lk.ijse.pahasarastudiospringfinal.util;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MappingUtil {

    // Convert User Entity to User DTO
    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null, // Never send password back to the UI
                user.getEmail(),
                user.getRole()
        );
    }

    // Convert User DTO to User Entity
    public User toUserEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        // Password encoding happens in ServiceImpl
        return user;
    }
}