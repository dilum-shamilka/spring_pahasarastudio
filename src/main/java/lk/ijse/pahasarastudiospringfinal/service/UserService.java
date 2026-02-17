package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO dto);
    UserDTO updateUser(UserDTO dto);
    String deleteUser(Long id);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
}
