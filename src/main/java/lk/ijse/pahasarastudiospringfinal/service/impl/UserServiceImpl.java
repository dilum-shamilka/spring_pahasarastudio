package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.AuthDTO;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String saveUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return VarList.RSP_DUPLICATED;
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (user.getRole() == null) user.setRole("USER");
        userRepo.save(user);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        // Find by Long ID
        Optional<User> userOptional = userRepo.findById((long) userDTO.getUserId());

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Manual mapping to avoid Optimistic Locking Error (code 05)
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setRole(userDTO.getRole());

            // Only update password if provided
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            userRepo.save(existingUser);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    @Override
    public String deleteUser(int id) {
        if (userRepo.existsById((long) id)) {
            userRepo.deleteById((long) id);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO getUserById(int id) {
        return userRepo.findById((long) id)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .orElse(null);
    }

    @Override
    public boolean authenticate(AuthDTO authDTO) {
        return userRepo.findByUsername(authDTO.getUsername())
                .map(user -> passwordEncoder.matches(authDTO.getPassword(), user.getPassword()))
                .orElse(false);
    }

    // --- Overloads and Helper Methods ---

    @Override
    public UserDTO findByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .orElse(null);
    }

    @Override public int getTotalUserCount() { return (int) userRepo.count(); }
    @Override public boolean existsByEmail(String email) { return userRepo.existsByEmail(email); }
    @Override public String deleteUser(Long id) { return deleteUser(id.intValue()); }
    @Override public UserDTO getUserById(Long id) { return getUserById(id.intValue()); }
    @Override public UserDTO findByEmail(String email) { return null; }
    @Override public boolean updateUserInfo(Long id, UserDTO userDTO) { return false; }
    @Override public boolean updateUserRole(int id, String role) { return false; }
    @Override public boolean updateUserRole(Long id, String role) { return false; }
}