package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();
    void saveClient(ClientDTO clientDTO);
    void deleteClient(Long id);
}