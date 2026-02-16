package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.EquipmentService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@CrossOrigin
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            String res = equipmentService.saveEquipment(equipmentDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Equipment Saved Successfully", equipmentDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Equipment Already Exists", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            String res = equipmentService.updateEquipment(equipmentDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Equipment Updated Successfully", equipmentDTO));
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Equipment Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllEquipment() {
        try {
            List<EquipmentDTO> equipmentList = equipmentService.getAllEquipment();
            if (!equipmentList.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", equipmentList));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Equipment Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{equipmentID}")
    public ResponseEntity<ResponseDTO> deleteEquipment(@PathVariable Long equipmentID) {
        try {
            String res = equipmentService.deleteEquipment(equipmentID);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Equipment Deleted Successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Equipment Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }
}