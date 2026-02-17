package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.PhotoFrameDTO;
import lk.ijse.pahasarastudiospringfinal.entity.PhotoFrame;
import lk.ijse.pahasarastudiospringfinal.repo.PhotoFrameRepo;
import lk.ijse.pahasarastudiospringfinal.repo.PhotoFrameRepo;
import lk.ijse.pahasarastudiospringfinal.service.PhotoFrameService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoFrameServiceImpl implements PhotoFrameService {

    private final PhotoFrameRepo repository;
    private final MappingUtil mappingUtil;

    @Override
    public PhotoFrameDTO saveFrame(PhotoFrameDTO dto) {
        PhotoFrame entity = mappingUtil.toPhotoFrameEntity(dto);
        entity.setId(null); // Ensure @GeneratedValue works
        return mappingUtil.toPhotoFrameDTO(repository.save(entity));
    }

    @Override
    public void updateFrame(Long id, PhotoFrameDTO dto) {
        PhotoFrame existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frame Not Found"));
        existing.setFrameName(dto.getFrameName());
        existing.setMaterial(dto.getMaterial());
        existing.setSize(dto.getSize());
        existing.setUnitPrice(dto.getUnitPrice());
        existing.setQtyOnHand(dto.getQtyOnHand());
        // @Transactional handles save
    }

    @Override
    public void deleteFrame(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("Frame Not Found");
        repository.deleteById(id);
    }

    @Override
    public PhotoFrameDTO getFrame(Long id) {
        return repository.findById(id)
                .map(mappingUtil::toPhotoFrameDTO)
                .orElseThrow(() -> new RuntimeException("Frame Not Found"));
    }

    @Override
    public List<PhotoFrameDTO> getAllFrames() {
        return repository.findAll().stream()
                .map(mappingUtil::toPhotoFrameDTO)
                .collect(Collectors.toList());
    }
}
