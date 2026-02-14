package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import java.util.List;

public interface EquipmentService {

    // ✅ Saves new gear and returns VarList code (00, 06, etc.)
    String saveEquipment(@Valid EquipmentDTO equipmentDTO);

    // ✅ Standardized update for the PutMapping in EquipmentController
    String updateEquipment(EquipmentDTO equipmentDTO);

    // ✅ Matches @DeleteMapping("/delete/{equipmentID}")
    String deleteEquipment(int id);

    List<EquipmentDTO> getAllEquipment();

    EquipmentDTO getEquipmentById(int id);

    EquipmentDTO getEquipmentById(Long id);

    EquipmentDTO getEquipmentBySerialNumber(String serialNumber);

    // Functional method for the Studio dashboard to toggle availability
    boolean updateEquipmentStatus(int id, String status);

    boolean updateEquipmentStatus(Long id, String status);

    String updateEquipment(Long id, EquipmentDTO equipmentDTO);

    String deleteEquipment(Long id);

    // Filtering for availability or maintenance tracking
    List<EquipmentDTO> getEquipmentByStatus(String status);

    int getTotalEquipmentCount();
}