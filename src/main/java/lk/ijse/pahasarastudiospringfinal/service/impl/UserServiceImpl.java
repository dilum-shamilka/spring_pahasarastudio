package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        // Encrypting password before saving to DB
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_ADMIN");
        userRepo.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserDTO(u.getId(), u.getUsername(), null, u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return new UserDTO(user.getId(), user.getUsername(), null, user.getEmail(), user.getRole());
    }
}