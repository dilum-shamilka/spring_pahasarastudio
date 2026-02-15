package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.InventoryService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@CrossOrigin // CRITICAL: Allows frontend to access the API
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveItem(@RequestBody InventoryDTO dto) {
        String res = inventoryService.saveItem(dto);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(res, "Item Added Successfully", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(res, "Item Already Exists", null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllItems() {
        List<InventoryDTO> items = inventoryService.getAllItems();
        return ResponseEntity.ok(
                new ResponseDTO(VarList.RSP_SUCCESS, "Success", items)
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateItem(@RequestBody InventoryDTO dto) {
        String res = inventoryService.updateItem(dto);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(new ResponseDTO(res, "Item Updated Successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(res, "Item Not Found", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteItem(@PathVariable Long id) {
        String res = inventoryService.deleteItem(id);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(new ResponseDTO(res, "Item Deleted Successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(res, "Item Not Found", null));
    }
}