package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.service.ClientService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private MappingUtil mapper;

    @Override
    public String saveClient(ClientDTO clientDTO) {
        if (clientRepo.existsByEmail(clientDTO.getEmail())) {
            return VarList.RSP_DUPLICATED;
        }
        clientRepo.save(mapper.toClientEntity(clientDTO));
        return VarList.RSP_SUCCESS;
    }

    @Override
    public String updateClient(ClientDTO clientDTO) {
        if (clientDTO.getId() == null) return VarList.RSP_ERROR;

        if (clientRepo.existsById(clientDTO.getId())) {
            // MappingUtil must ensure the ID is set in the Entity
            clientRepo.save(mapper.toClientEntity(clientDTO));
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll().stream()
                .map(mapper::toClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteClient(Long id) {
        if (clientRepo.existsById(id)) {
            clientRepo.deleteById(id);
            return VarList.RSP_SUCCESS;
        }
        return VarList.RSP_NO_DATA_FOUND;
    }

    // Bridge methods for interface compatibility
    @Override public String deleteClient(int id) { return deleteClient((long) id); }
    @Override public ClientDTO getClientById(int id) { return getClientById((long) id); }
    @Override public ClientDTO getClientById(Long id) {
        return clientRepo.findById(id).map(mapper::toClientDTO).orElse(null);
    }
    @Override public String updateClient(Long id, ClientDTO clientDTO) {
        clientDTO.setId(id);
        return updateClient(clientDTO);
    }
    @Override public int getTotalClientCount() { return (int) clientRepo.count(); }
    @Override public boolean existsByEmail(String email) { return clientRepo.existsByEmail(email); }
    @Override public ClientDTO getClientByEmail(String email) {
        return clientRepo.findByEmail(email).map(mapper::toClientDTO).orElse(null);
    }
}