package lk.ijse.pahasarastudiospringfinal.service.serviceimpl;

import lk.ijse.pahasarastudiospringfinal.dto.ClientDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.service.ClientService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    public ClientServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll().stream().map(c -> {
            ClientDTO dto = new ClientDTO();
            dto.setId(c.getId());
            dto.setFullName(c.getFullName());
            dto.setEmail(c.getEmail());
            dto.setPhoneNumber(c.getPhoneNumber());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveClient(ClientDTO dto) {
        Client client = new Client();
        client.setFullName(dto.getFullName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        clientRepo.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }
}