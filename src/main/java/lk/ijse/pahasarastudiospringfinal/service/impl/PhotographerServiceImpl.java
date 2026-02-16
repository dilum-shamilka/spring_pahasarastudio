package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.PhotographerDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Photographer;
import lk.ijse.pahasarastudiospringfinal.repo.PhotographerRepo;
import lk.ijse.pahasarastudiospringfinal.service.PhotographerService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotographerServiceImpl implements PhotographerService {

    @Autowired
    private PhotographerRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public String savePhotographer(PhotographerDTO dto) {
        if(repo.existsByEmail(dto.getEmail())) return VarList.RSP_DUPLICATED;
        Photographer photographer = mapper.map(dto, Photographer.class);
        repo.save(photographer);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updatePhotographer(PhotographerDTO dto) {
        Optional<Photographer> opt = repo.findById(dto.getId());
        if(opt.isPresent()){
            Photographer p = opt.get();
            p.setName(dto.getName());
            p.setEmail(dto.getEmail());
            p.setSalary(dto.getSalary());
            p.setStatus(dto.getStatus());
            repo.save(p);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public String deletePhotographer(Long id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<PhotographerDTO> getAllPhotographers() {
        return repo.findAll().stream()
                .map(p -> mapper.map(p, PhotographerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhotographerDTO getPhotographerById(Long id) {
        return repo.findById(id).map(p -> mapper.map(p, PhotographerDTO.class)).orElse(null);
    }
}
