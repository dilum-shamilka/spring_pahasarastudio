package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.PhotographerDTO;

import java.util.List;

public interface PhotographerService {

    String savePhotographer(PhotographerDTO dto);
    String updatePhotographer(PhotographerDTO dto);
    String deletePhotographer(Long id);
    List<PhotographerDTO> getAllPhotographers();
    PhotographerDTO getPhotographerById(Long id);
}
