package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getAllItems();
    void updateStock(Long id, int quantity);
}