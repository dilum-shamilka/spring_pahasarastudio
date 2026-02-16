package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.AuthDTO;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;

import java.util.List;

public interface UserService {

    String saveUser(UserDTO userDTO);
    String updateUser(UserDTO userDTO);
    String deleteUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    boolean authenticate(AuthDTO authDTO);
}
