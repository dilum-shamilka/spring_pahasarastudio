package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.StudioServiceDTO;
import lk.ijse.pahasarastudiospringfinal.entity.StudioServiceEntity;
import lk.ijse.pahasarastudiospringfinal.repo.StudioServiceRepo;
import lk.ijse.pahasarastudiospringfinal.service.StudioService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudioServiceImpl implements StudioService {

    private final StudioServiceRepo repo;
    private final MappingUtil mapper;

    public StudioServiceImpl(StudioServiceRepo repo, MappingUtil mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public String saveService(StudioServiceDTO dto) {
        if (repo.existsByServiceName(dto.getServiceName())) {
            return VarList.RSP_DUPLICATED;
        }

        // 1. Convert DTO to Entity
        StudioServiceEntity entity = mapper.toServiceEntity(dto);

        // 2. Save and catch the returned Entity (which now has an ID)
        StudioServiceEntity savedEntity = repo.save(entity);

        // 3. Update the original DTO with the new ID for the response
        dto.setId(savedEntity.getId());

        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateService(Long id, StudioServiceDTO dto) {
        Optional<StudioServiceEntity> optional = repo.findById(id);
        if (optional.isEmpty()) return VarList.RSP_NO_DATA_FOUND;

        StudioServiceEntity entity = optional.get();

        if (dto.getServiceName() != null && !dto.getServiceName().equals(entity.getServiceName())) {
            if (repo.existsByServiceName(dto.getServiceName())) return VarList.RSP_DUPLICATED;
            entity.setServiceName(dto.getServiceName());
        }

        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

        repo.save(entity);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteService(Long id) {
        if (!repo.existsById(id)) return VarList.RSP_NO_DATA_FOUND;
        repo.deleteById(id);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public List<StudioServiceDTO> getAllServices() {
        return repo.findAll().stream().map(mapper::toServiceDTO).collect(Collectors.toList());
    }

    @Override
    public StudioServiceDTO getServiceById(Long id) {
        return repo.findById(id).map(mapper::toServiceDTO).orElse(null);
    }
}