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
        try {
            String res = clientService.saveClient(clientDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Client Registered Successfully", clientDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Email Already Exists", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateClient(@RequestBody ClientDTO clientDTO) {
        try {
            String res = clientService.updateClient(clientDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Client Updated Successfully", clientDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Client Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<ResponseDTO> deleteClient(@PathVariable Long clientId) {
        try {
            String res = clientService.deleteClient(clientId);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Deleted", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", clients));
    }
}