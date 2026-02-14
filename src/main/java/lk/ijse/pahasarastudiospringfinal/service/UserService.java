package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.AuthDTO;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface UserService {

    // ✅ Returns String to match VarList.RSP_SUCCESS etc.
    String saveUser(@Valid UserDTO userDTO);

    // ✅ Returns String for update status
    String updateUser(UserDTO userDTO);

    // ✅ Returns String for delete status
    String deleteUser(int id);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(int id);

    UserDTO getUserById(Long id);

    UserDTO findByUsername(String username);

    // ✅ Added for AuthController login logic
    boolean authenticate(AuthDTO authDTO);

    UserDTO findByEmail(String email);

    boolean updateUserInfo(Long id, UserDTO userDTO);

    String deleteUser(Long id);

    int getTotalUserCount();

    boolean existsByEmail(String email);

    // Matches the "status update" pattern for roles
    boolean updateUserRole(int id, String role);

    boolean updateUserRole(Long id, String role);
}