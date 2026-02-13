package lk.ijse.pahasarastudiospringfinal.service.serviceimpl;

import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Equipment;
import lk.ijse.pahasarastudiospringfinal.repo.EquipmentRepo;
import lk.ijse.pahasarastudiospringfinal.service.EquipmentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepo equipmentRepo;

    public EquipmentServiceImpl(EquipmentRepo equipmentRepo) {
        this.equipmentRepo = equipmentRepo;
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentRepo.findAll().stream().map(e -> {
            EquipmentDTO dto = new EquipmentDTO();
            dto.setId(e.getId());
            dto.setName(e.getName());
            dto.setAvailable(e.isAvailable());
            return dto;
        }).toList();
    }

    @Override
    public void updateStatus(Long id, boolean isAvailable) {
        Equipment equipment = equipmentRepo.findById(id).orElseThrow();
        equipment.setAvailable(isAvailable);
        equipmentRepo.save(equipment);
    }
}