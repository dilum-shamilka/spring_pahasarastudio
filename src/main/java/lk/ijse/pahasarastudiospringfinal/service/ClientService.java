package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    String saveClient(ClientDTO clientDTO);

    String updateClient(ClientDTO clientDTO);

    String deleteClient(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long id);

    ClientDTO getClientByEmail(String email);

    int getTotalClientCount();

    boolean existsByEmail(String email);
}