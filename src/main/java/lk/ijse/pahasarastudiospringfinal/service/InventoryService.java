package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {

    String saveItem(InventoryDTO inventoryDTO);

    String updateItem(InventoryDTO inventoryDTO);

    List<InventoryDTO> getLowStockItems();

    int getTotalItemCount();

    // Standard bridges for other interface methods
    InventoryDTO getItemById(int id);

    String updateStock(Long id, int quantity);

    int updateItem(Long id, InventoryDTO inventoryDTO);

    InventoryDTO getItemById(Long id);

    InventoryDTO getItemByName(String itemName);

    String updateStock(int id, int quantity);

    String deleteItem(Long itemId);

    String deleteItem(int itemId);

    List<InventoryDTO> getAllItems();
}