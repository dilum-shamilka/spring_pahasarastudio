package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.AuthDTO;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String saveUser(UserDTO userDTO) {
        if(userRepo.existsByEmail(userDTO.getEmail())) return VarList.RSP_DUPLICATED;
        if(userRepo.existsByUsername(userDTO.getUsername())) return VarList.RSP_DUPLICATED;

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = mappingUtil.toUserEntity(userDTO, encodedPassword);

        userRepo.save(user);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        Optional<User> userOpt = userRepo.findById(userDTO.getUserId());
        if(userOpt.isPresent()){
            User existingUser = userOpt.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setRole(userDTO.getRole());

            if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            userRepo.save(existingUser);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deleteUser(Long id) {
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(mappingUtil::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepo.findById(id).map(mappingUtil::toUserDTO).orElse(null);
    }

    @Override
    public boolean authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = userRepo.findByUsername(authDTO.getUsername());
        return userOpt.isPresent() && passwordEncoder.matches(authDTO.getPassword(), userOpt.get().getPassword());
    }
}
