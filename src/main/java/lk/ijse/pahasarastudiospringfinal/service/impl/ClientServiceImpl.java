package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.service.ClientService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final MappingUtil mapper;

    public ClientServiceImpl(ClientRepo clientRepo, MappingUtil mapper) {
        this.clientRepo = clientRepo;
        this.mapper = mapper;
    }

    @Override
    public String saveClient(ClientDTO clientDTO) {
        if (clientRepo.existsByEmail(clientDTO.getEmail())) {
            return VarList.RSP_DUPLICATED;
        }
        Client client = mapper.toClientEntity(clientDTO);
        clientRepo.save(client);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateClient(ClientDTO clientDTO) {
        if (clientDTO.getId() == null) return VarList.RSP_ERROR;
        if (!clientRepo.existsById(clientDTO.getId())) return VarList.RSP_NO_DATA_FOUND;

        Client updated = mapper.toClientEntity(clientDTO);
        clientRepo.save(updated);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String deleteClient(Long id) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) return VarList.RSP_NO_DATA_FOUND;

        // ✅ Prevent deletion if client has bookings
        if (client.getBookings() != null && !client.getBookings().isEmpty()) {
            return VarList.RSP_FOREIGN_KEY_VIOLATION;
        }

        clientRepo.deleteById(id);
        return VarList.RSP_SUCCESS;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll()
                .stream()
                .map(mapper::toClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepo.findById(id)
                .map(mapper::toClientDTO)
                .orElse(null);
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        return clientRepo.findByEmail(email)
                .map(mapper::toClientDTO)
                .orElse(null);
    }

    @Override
    public int getTotalClientCount() {
        return (int) clientRepo.count();
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepo.existsByEmail(email);
    }
}