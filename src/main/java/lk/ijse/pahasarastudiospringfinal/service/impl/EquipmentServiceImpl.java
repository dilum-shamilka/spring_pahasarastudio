package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Equipment;
import lk.ijse.pahasarastudiospringfinal.repo.EquipmentRepo;
import lk.ijse.pahasarastudiospringfinal.service.EquipmentService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepo equipmentRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String saveEquipment(EquipmentDTO equipmentDTO) {
        if (equipmentRepo.existsBySerialNumber(equipmentDTO.getSerialNumber())) {
            return VarList.RSP_DUPLICATED;
        }
        equipmentRepo.save(mapper.toEquipmentEntity(equipmentDTO));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateEquipment(EquipmentDTO equipmentDTO) {
        // Double check existence
        if (equipmentDTO.getId() != null && equipmentRepo.existsById(equipmentDTO.getId())) {

            // Map DTO to Entity
            Equipment equipment = mapper.toEquipmentEntity(equipmentDTO);

            // IMPORTANT: Ensure the Entity has the ID from the DTO
            equipment.setId(equipmentDTO.getId());

            equipmentRepo.save(equipment); // This will now perform an UPDATE
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deleteEquipment(Long id) {
        if (id != null && equipmentRepo.existsById(id)) {
            equipmentRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentRepo.findAll().stream()
                .map(mapper::toEquipmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {
        return equipmentRepo.findById(id).map(mapper::toEquipmentDTO).orElse(null);
    }

    @Override
    public EquipmentDTO getEquipmentBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public boolean updateEquipmentStatus(Long id, String status) {
        return false;
    }

    @Override
    public List<EquipmentDTO> getEquipmentByStatus(String status) {
        return List.of();
    }

    @Override
    public int getTotalEquipmentCount() {
        return 0;
    }

    // ... rest of the methods remain as per your previous implementation
}