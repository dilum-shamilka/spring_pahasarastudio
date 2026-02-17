package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.*;
import lk.ijse.pahasarastudiospringfinal.service.AlbumService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/albums")
@CrossOrigin
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody AlbumDTO dto) {

        String res = albumService.saveAlbum(dto);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO("00", "Album Saved Successfully", null));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO("01", "Client Not Found", null));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody AlbumDTO dto) {

        String res = albumService.updateAlbum(dto);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(
                    new ResponseDTO("00", "Album Updated Successfully", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("01", "Album Not Found", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {

        String res = albumService.deleteAlbum(id);

        if (res.equals(VarList.RSP_SUCCESS)) {
            return ResponseEntity.ok(
                    new ResponseDTO("00", "Album Deleted Successfully", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("01", "Album Not Found", null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {

        List<AlbumDTO> list = albumService.getAllAlbums();

        return ResponseEntity.ok(
                new ResponseDTO("00", "Success", list));
    }
}
