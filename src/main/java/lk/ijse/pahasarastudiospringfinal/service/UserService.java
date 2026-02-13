package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO findByUsername(String username);
}