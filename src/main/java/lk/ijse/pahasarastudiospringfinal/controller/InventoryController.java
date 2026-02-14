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
@CrossOrigin
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // --- SAVE ITEM ---
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveItem(@RequestBody InventoryDTO inventoryDTO) {
        try {
            String res = inventoryService.saveItem(inventoryDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Item Added Successfully", inventoryDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Item Already Exists", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- UPDATE ITEM ---
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateItem(@RequestBody InventoryDTO inventoryDTO) {
        try {
            String res = inventoryService.updateItem(inventoryDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Item Updated Successfully", inventoryDTO));
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Item Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- GET ALL ---
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllItems() {
        try {
            List<InventoryDTO> items = inventoryService.getAllItems();
            if (items != null && !items.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", items));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Inventory Items Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- DELETE ITEM ---
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ResponseDTO> deleteItem(@PathVariable int itemId) {
        try {
            String res = inventoryService.deleteItem(itemId);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Item Deleted Successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Item Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }
}