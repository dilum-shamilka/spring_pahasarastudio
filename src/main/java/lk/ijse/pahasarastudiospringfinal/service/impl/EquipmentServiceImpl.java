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
        if (equipmentRepo.existsById(equipmentDTO.getId())) {
            equipmentRepo.save(mapper.toEquipmentEntity(equipmentDTO));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deleteEquipment(int id) {
        long longId = (long) id;
        if (equipmentRepo.existsById(longId)) {
            equipmentRepo.deleteById(longId);
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

    // Complete the remaining interface bridges
    @Override
    public EquipmentDTO getEquipmentById(int id) {
        return equipmentRepo.findById((long) id).map(mapper::toEquipmentDTO).orElse(null);
    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {
        return null;
    }

    @Override
    public EquipmentDTO getEquipmentBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public boolean updateEquipmentStatus(int id, String status) {
        return false;
    }

    @Override
    public boolean updateEquipmentStatus(Long id, String status) {
        return false;
    }

    @Override
    public String updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        equipmentDTO.setId(id);
        return updateEquipment(equipmentDTO);
    }

    @Override
    public String deleteEquipment(Long id) {
        return deleteEquipment(id.intValue());
    }

    @Override
    public List<EquipmentDTO> getEquipmentByStatus(String status) {
        return List.of();
    }

    @Override
    public int getTotalEquipmentCount() {
        return (int) equipmentRepo.count();
    }

    // ... Implement other status filtering methods using mapper.toEquipmentDTO
}