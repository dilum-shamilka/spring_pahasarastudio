package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.SaleDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Sale;
import lk.ijse.pahasarastudiospringfinal.repo.SaleRepository;
import lk.ijse.pahasarastudiospringfinal.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public SaleDTO saveSale(SaleDTO dto) {
        Sale sale = new Sale();
        sale.setFrameName(dto.getFrameName());
        sale.setSize(dto.getSize());
        sale.setQty(dto.getQty());
        sale.setTotal(dto.getTotal());
        sale.setSaleTime(LocalDateTime.now()); // Set current server time

        Sale saved = saleRepository.save(sale);
        dto.setId(saved.getId());
        dto.setSaleTime(saved.getSaleTime().toString());
        return dto;
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAllByOrderBySaleTimeDesc().stream()
                .map(s -> new SaleDTO(
                        s.getId(),
                        s.getFrameName(),
                        s.getSize(),
                        s.getQty(),
                        s.getTotal(),
                        s.getSaleTime().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void clearHistory() {
        saleRepository.deleteAll();
    }
}