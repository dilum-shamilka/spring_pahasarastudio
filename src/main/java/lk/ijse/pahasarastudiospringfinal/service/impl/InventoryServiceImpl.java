package lk.ijse.pahasarastudiospringfinal.service.serviceimpl;

import lk.ijse.pahasarastudiospringfinal.dto.InventoryDTO;
import lk.ijse.pahasarastudiospringfinal.repo.InventoryRepo;
import lk.ijse.pahasarastudiospringfinal.service.InventoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;

    public InventoryServiceImpl(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public List<InventoryDTO> getAllItems() {
        return inventoryRepo.findAll().stream().map(item -> {
            InventoryDTO dto = new InventoryDTO();
            dto.setId(item.getId());
            dto.setItemName(item.getItemName());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).toList();
    }

    @Override
    public void updateStock(Long id, int quantity) {
        var item = inventoryRepo.findById(id).orElseThrow();
        item.setQuantity(quantity);
        inventoryRepo.save(item);
    }
}