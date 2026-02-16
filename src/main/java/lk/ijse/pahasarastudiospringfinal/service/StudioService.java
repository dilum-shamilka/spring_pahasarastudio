package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import java.util.List;

public interface StudioService {

    String saveService(StudioServiceDTO dto);

    String updateService(Long id, StudioServiceDTO dto);

    String deleteService(Long id);

    List<StudioServiceDTO> getAllServices();

    StudioServiceDTO getServiceById(Long id);
}