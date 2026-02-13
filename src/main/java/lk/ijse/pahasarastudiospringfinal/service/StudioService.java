package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import java.util.List;

public interface StudioService {
    List<StudioServiceDTO> getAllServices();
    void saveService(StudioServiceDTO serviceDTO);
}