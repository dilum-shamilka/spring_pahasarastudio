package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.SaleDTO;
import lk.ijse.pahasarastudiospringfinal.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@CrossOrigin // Adjust based on your security config
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDTO> recordSale(@RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.saveSale(saleDTO));
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getHistory() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @DeleteMapping
    public ResponseEntity<String> clearHistory() {
        saleService.clearHistory();
        return ResponseEntity.ok("History cleared");
    }
}