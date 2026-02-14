package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {

    // ✅ Saves a new item and returns VarList code (00, 06, etc.)
    String saveItem(@Valid InventoryDTO inventoryDTO);

    // ✅ Standardized update for the PutMapping in InventoryController
    String updateItem(InventoryDTO inventoryDTO);

    // ✅ Standardized delete matching Controller's @PathVariable int itemId
    String deleteItem(int itemId);

    List<InventoryDTO> getAllItems();

    InventoryDTO getItemById(int id);

    String updateStock(Long id, int quantity);

    int updateItem(Long id, InventoryDTO inventoryDTO);

    InventoryDTO getItemById(Long id);

    InventoryDTO getItemByName(String itemName);

    // Business logic for specific stock adjustments (e.g., after a shoot)
    String updateStock(int id, int quantity);

    String deleteItem(Long id);

    // For dashboard alerts (where quantity <= reorderLevel)
    List<InventoryDTO> getLowStockItems();

    int getTotalItemCount();
}