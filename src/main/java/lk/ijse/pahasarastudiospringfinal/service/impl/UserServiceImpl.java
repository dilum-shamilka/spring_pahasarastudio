package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final MappingUtil mappingUtil;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection ensures all beans (including PasswordEncoder) are present at startup
    public UserServiceImpl(UserRepo userRepo, MappingUtil mappingUtil, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mappingUtil = mappingUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        User user = mappingUtil.toUserEntity(dto);
        // Encrypting password before saving to DB
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        return mappingUtil.toUserDTO(userRepo.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        User existingUser = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));

        // Update fields
        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());

        // Only re-encode and update password if a new one is provided
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return mappingUtil.toUserDTO(userRepo.save(existingUser));
    }

    @Override
    public String deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found!");
        }
        userRepo.deleteById(id);
        return "SUCCESS";
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepo.findById(id)
                .map(mappingUtil::toUserDTO)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> {
                    UserDTO dto = mappingUtil.toUserDTO(user);
                    dto.setPassword(null); // Safety: Never send hashed passwords back to UI
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Converts our custom User entity into Spring Security's UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public User loadUserEntityByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}