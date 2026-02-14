package lk.ijse.pahasarastudiospringfinal.service;

import jakarta.validation.Valid;
import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import java.util.List;

public interface ClientService {

    // ✅ Saves a new client and returns VarList code (00 for Success, 06 for Duplicate)
    String saveClient(@Valid ClientDTO clientDTO);

    // ✅ Standardized update for the PutMapping in ClientController
    String updateClient(ClientDTO clientDTO);

    // ✅ Matches @DeleteMapping("/delete/{clientId}")
    String deleteClient(int id);

    List<ClientDTO> getAllClients();

    ClientDTO getClientById(int id);

    ClientDTO getClientById(Long id);

    ClientDTO getClientByEmail(String email);

    String updateClient(Long id, ClientDTO clientDTO);

    String deleteClient(Long id);

    // Essential for dashboard and reporting
    int getTotalClientCount();

    boolean existsByEmail(String email);
}