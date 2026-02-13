package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.StudioService;
import lk.ijse.pahasarastudiospringfinal.repo.StudioServiceRepo;
//import lk.ijse.pahasarastudiospringfinal.service.StudioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioServiceImpl extends StudioService {
    private final StudioServiceRepo studioServiceRepo;

    public StudioServiceImpl(StudioServiceRepo studioServiceRepo) {
        this.studioServiceRepo = studioServiceRepo;
    }

    @Override
    public List<StudioServiceDTO> getAllServices() {
        return studioServiceRepo.findAll().stream().map(s -> {
            StudioServiceDTO dto = new StudioServiceDTO();
            dto.setId(s.getId());
            dto.setServiceName(s.getServiceName());
            dto.setPrice(s.getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveService(StudioServiceDTO dto) {
        StudioService entity = new StudioService() {
            @Override
            public List<StudioServiceDTO> getAllServices() {
                return List.of();
            }

            @Override
            public void saveService(StudioServiceDTO dto) {

            }
        };
        entity.setServiceName(dto.getServiceName());
        entity.setPrice(dto.getPrice());
        studioServiceRepo.save(entity);
    }
}