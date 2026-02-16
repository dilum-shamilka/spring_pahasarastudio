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
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveUser(@RequestBody UserDTO userDTO){
        String res = userService.saveUser(userDTO);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.RSP_SUCCESS,"Saved",userDTO));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(VarList.RSP_DUPLICATED,"User Already Exists",null));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO){
        String res = userService.updateUser(userDTO);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Updated",userDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"User Not Found",null));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long userId){
        String res = userService.deleteUser(userId);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Deleted",null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"User Not Found",null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Success",users));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long userId){
        UserDTO user = userService.getUserById(userId);
        if(user != null){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Success",user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"User Not Found",null));
    }
}
