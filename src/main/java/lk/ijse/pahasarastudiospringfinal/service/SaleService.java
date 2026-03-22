package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.SaleDTO;
import java.util.List;

public interface SaleService {
    SaleDTO saveSale(SaleDTO saleDTO);
    List<SaleDTO> getAllSales();
    void clearHistory();
}