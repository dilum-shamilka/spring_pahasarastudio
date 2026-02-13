package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.EquipmentDTO;
import java.util.List;

public interface EquipmentService {
    List<EquipmentDTO> getAllEquipment();
    void updateStatus(Long id, boolean isAvailable);
}