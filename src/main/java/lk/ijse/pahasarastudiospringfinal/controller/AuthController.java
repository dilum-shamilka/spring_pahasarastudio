package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.*;
import lk.ijse.pahasarastudiospringfinal.entity.User;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.JwtUtil;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MappingUtil mappingUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthDTO authDTO) {
        try {
            // Fetch actual User entity from DB
            User user = userService.loadUserEntityByEmail(authDTO.getEmail());

            if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO("01", "Authentication Failed: Wrong Password", null));
            }

            UserDTO userDTO = mappingUtil.toUserDTO(user);
            String token = jwtUtil.generateToken(userDTO);

            return ResponseEntity.ok(new ResponseDTO("00", "Login Successful", token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("01", e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        return ResponseEntity.ok(new ResponseDTO("00", "Session Cleared", null));
    }
}
