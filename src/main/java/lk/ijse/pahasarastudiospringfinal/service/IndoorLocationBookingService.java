package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.IndoorLocationBookingDTO;

import java.util.List;

public interface IndoorLocationBookingService {

    IndoorLocationBookingDTO save(IndoorLocationBookingDTO dto);

    IndoorLocationBookingDTO update(Long id, IndoorLocationBookingDTO dto);

    void delete(Long id);

    IndoorLocationBookingDTO getById(Long id);

    List<IndoorLocationBookingDTO> getAll();
}
