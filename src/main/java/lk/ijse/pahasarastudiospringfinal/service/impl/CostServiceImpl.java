package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.CostDTO;
import lk.ijse.pahasarastudiospringfinal.entity.CostManagement;
import lk.ijse.pahasarastudiospringfinal.repo.CostRepo;
import lk.ijse.pahasarastudiospringfinal.service.CostService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CostServiceImpl implements CostService {

    @Autowired
    private CostRepo costRepo;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveCost(CostDTO dto) {
        costRepo.save(mappingUtil.toCostEntity(dto));
    }

    @Override
    public void updateCost(CostDTO dto) {
        if (costRepo.existsById(dto.getCostId())) {
            costRepo.save(mappingUtil.toCostEntity(dto));
        }
    }

    @Override
    public void deleteCost(Long id) {
        costRepo.deleteById(id);
    }

    @Override
    public List<CostDTO> getAllCosts() {
        return mappingUtil.toCostDTOList(costRepo.findAll());
    }
}