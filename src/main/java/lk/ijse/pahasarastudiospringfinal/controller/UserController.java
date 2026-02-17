package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin // Allows frontend to communicate with backend
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(new ResponseDTO("00", "Success", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("05", e.getMessage(), null));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUser(id);
            return ResponseEntity.ok(new ResponseDTO("00", "Success", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("01", e.getMessage(), null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveUser(@RequestBody UserDTO dto) {
        try {
            UserDTO savedUser = userService.saveUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO("00", "User Saved Successfully", savedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("05", e.getMessage(), null));
        }
    }

    @PutMapping("/update") // Keep this consistent with the JS fetch call
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO dto) {
        try {
            UserDTO updatedUser = userService.updateUser(dto);
            return ResponseEntity.ok(new ResponseDTO("00", "User Updated Successfully", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("01", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ResponseDTO("00", "User Deleted Successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("01", e.getMessage(), null));
        }
    }
}