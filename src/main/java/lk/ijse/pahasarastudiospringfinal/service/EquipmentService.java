package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import java.util.List;

public interface EquipmentService {

    String saveEquipment(@Valid EquipmentDTO equipmentDTO);

    String updateEquipment(EquipmentDTO equipmentDTO);

    String deleteEquipment(Long id);

    List<EquipmentDTO> getAllEquipment();

    EquipmentDTO getEquipmentById(Long id);

    EquipmentDTO getEquipmentBySerialNumber(String serialNumber);

    boolean updateEquipmentStatus(Long id, String status);

    List<EquipmentDTO> getEquipmentByStatus(String status);

    int getTotalEquipmentCount();
}