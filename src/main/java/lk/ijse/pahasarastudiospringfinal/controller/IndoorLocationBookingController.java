package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.IndoorLocationBookingDTO;
import lk.ijse.pahasarastudiospringfinal.service.IndoorLocationBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/indoor-booking")
@RequiredArgsConstructor
@CrossOrigin
public class IndoorLocationBookingController {

    private final IndoorLocationBookingService service;

    @PostMapping
    public IndoorLocationBookingDTO save(
            @RequestBody IndoorLocationBookingDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public IndoorLocationBookingDTO update(
            @PathVariable Long id,
            @RequestBody IndoorLocationBookingDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public IndoorLocationBookingDTO getById(
            @PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<IndoorLocationBookingDTO> getAll() {
        return service.getAll();
    }
}
