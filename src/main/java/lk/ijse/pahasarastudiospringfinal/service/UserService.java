package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO saveUser(UserDTO dto);
    UserDTO updateUser(UserDTO dto);
    String deleteUser(Long id);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();

    // New method to fetch User entity by email
    lk.ijse.pahasarastudiospringfinal.entity.User loadUserEntityByEmail(String email);
}
