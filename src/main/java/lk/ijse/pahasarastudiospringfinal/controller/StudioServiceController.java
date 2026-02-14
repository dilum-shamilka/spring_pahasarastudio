package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import lk.ijse.pahasarastudiospringfinal.service.StudioService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/services")
@CrossOrigin
public class StudioServiceController {

    private final StudioService studioService;

    public StudioServiceController(StudioService studioService) {
        this.studioService = studioService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody StudioServiceDTO dto) {
        String res = studioService.saveService(dto);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(res, "Success", dto));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO(res, "Duplicate Name", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody StudioServiceDTO dto) {
        String res = studioService.updateService(id, dto);
        if (res.equals(VarList.RSP_SUCCESS)) return ResponseEntity.ok(new ResponseDTO(res, "Updated", dto));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(res, "Not Found", null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StudioServiceDTO>> getAll() {
        return ResponseEntity.ok(studioService.getAllServices());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        String res = studioService.deleteService(id);
        if (res.equals(VarList.RSP_SUCCESS)) return ResponseEntity.ok(new ResponseDTO(res, "Deleted", null));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(res, "Not Found", null));
    }
}