package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.PhotoFrameDTO;
import lk.ijse.pahasarastudiospringfinal.service.PhotoFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photoframe")
@RequiredArgsConstructor
@CrossOrigin
public class PhotoFrameController {

    private final PhotoFrameService service;

    @PostMapping
    public ResponseEntity<PhotoFrameDTO> save(@RequestBody PhotoFrameDTO dto) {
        return new ResponseEntity<>(service.saveFrame(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoFrameDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFrame(id));
    }

    @GetMapping
    public ResponseEntity<List<PhotoFrameDTO>> getAll() {
        return ResponseEntity.ok(service.getAllFrames());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PhotoFrameDTO dto) {
        service.updateFrame(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFrame(id);
        return ResponseEntity.noContent().build();
    }
}
