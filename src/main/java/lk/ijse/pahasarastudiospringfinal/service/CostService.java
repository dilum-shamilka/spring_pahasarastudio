package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.CostDTO;
import java.util.List;

public interface CostService {
    void saveCost(CostDTO dto);
    void updateCost(CostDTO dto);
    void deleteCost(Long id);
    List<CostDTO> getAllCosts();
}