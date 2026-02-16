package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.PhotographerDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.PhotographerService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/photographers")
public class PhotographerController {

    private final PhotographerService service;

    public PhotographerController(PhotographerService service){
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody PhotographerDTO dto){
        String res = service.savePhotographer(dto);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.RSP_SUCCESS,"Saved",dto));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(res,"Duplicate Email",null));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody PhotographerDTO dto){
        String res = service.updatePhotographer(dto);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Updated",dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"Photographer not found",null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id){
        String res = service.deletePhotographer(id);
        if(res.equals(VarList.RSP_SUCCESS)){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Deleted",null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"Photographer not found",null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<PhotographerDTO> list = service.getAllPhotographers();
        return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Success",list));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id){
        PhotographerDTO dto = service.getPhotographerById(id);
        if(dto != null){
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS,"Success",dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND,"Photographer not found",null));
    }
}
