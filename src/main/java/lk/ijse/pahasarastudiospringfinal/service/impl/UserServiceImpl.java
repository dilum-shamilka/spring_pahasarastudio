package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MappingUtil mappingUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(UserDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) throw new RuntimeException("Email already exists!");
        User user = mappingUtil.toUserEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return mappingUtil.toUserDTO(userRepo.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        return userRepo.findById(dto.getUserId()).map(existing -> {
            User user = mappingUtil.toUserEntity(dto);
            if (dto.getPassword() != null && !dto.getPassword().isEmpty())
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            else user.setPassword(existing.getPassword());
            return mappingUtil.toUserDTO(userRepo.save(user));
        }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public String deleteUser(Long id) {
        if (!userRepo.existsById(id)) throw new RuntimeException("User not found!");
        userRepo.deleteById(id);
        return "SUCCESS";
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepo.findById(id).map(mappingUtil::toUserDTO)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(dto -> {
                    UserDTO u = mappingUtil.toUserDTO(dto);
                    u.setPassword(null); // hide password
                    return u;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    @Override
    public User loadUserEntityByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
