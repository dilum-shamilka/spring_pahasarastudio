package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Inventory;
import lk.ijse.pahasarastudiospringfinal.repo.InventoryRepo;
import lk.ijse.pahasarastudiospringfinal.service.InventoryService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String saveItem(InventoryDTO inventoryDTO) {
        if (inventoryRepo.existsByItemName(inventoryDTO.getItemName())) {
            return VarList.RSP_DUPLICATED;
        }
        inventoryRepo.save(mapper.toInventoryEntity(inventoryDTO));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateItem(InventoryDTO inventoryDTO) {
        if (inventoryRepo.existsById(inventoryDTO.getId())) {
            inventoryRepo.save(mapper.toInventoryEntity(inventoryDTO));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deleteItem(int itemId) {
        long id = (long) itemId;
        if (inventoryRepo.existsById(id)) {
            inventoryRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<InventoryDTO> getAllItems() {
        return inventoryRepo.findAll().stream()
                .map(mapper::toInventoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryDTO> getLowStockItems() {
        // Fetches items where quantity <= reorderLevel
        return inventoryRepo.findAll().stream()
                .filter(item -> item.getQuantity() <= item.getReorderLevel())
                .map(mapper::toInventoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalItemCount() {
        return (int) inventoryRepo.count();
    }

    // Standard bridges for other interface methods
    @Override public InventoryDTO getItemById(int id) { return inventoryRepo.findById((long)id).map(mapper::toInventoryDTO).orElse(null); }

    @Override
    public String updateStock(Long id, int quantity) {
        return "";
    }

    @Override
    public int updateItem(Long id, InventoryDTO inventoryDTO) {
        return 0;
    }

    @Override
    public InventoryDTO getItemById(Long id) {
        return null;
    }

    @Override
    public InventoryDTO getItemByName(String itemName) {
        return null;
    }

    @Override
    public String updateStock(int id, int quantity) {
        return "";
    }

    @Override public String deleteItem(Long id) { return deleteItem(id.intValue()); }
}