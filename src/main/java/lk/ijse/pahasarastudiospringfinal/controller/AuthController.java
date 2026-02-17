package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.AuthDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthDTO authDTO) {
        return userRepo.findAll().stream()
                .filter(u -> u.getEmail().equals(authDTO.getEmail()))
                .findFirst()
                .map(user -> {
                    if (passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
                        return ResponseEntity.ok(new ResponseDTO("00", "Success", user.getRole().name()));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO("01", "Wrong Password", null));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO("01", "User Not Found", null)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        return ResponseEntity.ok(new ResponseDTO("00", "Session Cleared", null));
    }
}