package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.StudioServiceEntity;
import lk.ijse.pahasarastudiospringfinal.repo.StudioServiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.StudioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioServiceImpl implements StudioService { // USE 'implements'

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
            dto.setDescription(s.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveService(StudioServiceDTO dto) {
        StudioServiceEntity entity = new StudioServiceEntity();
        entity.setServiceName(dto.getServiceName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        studioServiceRepo.save(entity);
    }
}