package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.ClientService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        String res = clientService.saveClient(clientDTO);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.RSP_SUCCESS, "Client Registered Successfully", clientDTO));
        } else if (res.equals(VarList.RSP_DUPLICATED)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Email Already Exists", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, "Something Went Wrong", null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateClient(@RequestBody ClientDTO clientDTO) {
        String res = clientService.updateClient(clientDTO);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Client Updated Successfully", clientDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Client Not Found", null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteClient(@PathVariable Long id) {
        String res = clientService.deleteClient(id);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Client Deleted Successfully", null));
        } else if (res.equals(VarList.RSP_FOREIGN_KEY_VIOLATION)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.RSP_FOREIGN_KEY_VIOLATION,
                            "Cannot delete client with active bookings", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Client Not Found", null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllClients() {
        List<ClientDTO> list = clientService.getAllClients();
        return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", list));
    }
}
