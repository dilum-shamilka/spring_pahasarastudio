package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.CostDTO;
import lk.ijse.pahasarastudiospringfinal.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/costs")
@CrossOrigin
public class CostController {

    @Autowired
    private CostService costService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCost(@RequestBody CostDTO costDTO) {
        costService.saveCost(costDTO);
        return ResponseEntity.ok("Cost Saved Successfully");
    }

    @GetMapping("/all")
    public List<CostDTO> getAllCosts() {
        return costService.getAllCosts();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCost(@RequestBody CostDTO costDTO) {
        costService.updateCost(costDTO);
        return ResponseEntity.ok("Cost Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCost(@PathVariable Long id) {
        costService.deleteCost(id);
        return ResponseEntity.ok("Cost Deleted");
    }
}