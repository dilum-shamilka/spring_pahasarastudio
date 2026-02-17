package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.AlbumDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Album;
import lk.ijse.pahasarastudiospringfinal.entity.Client;
import lk.ijse.pahasarastudiospringfinal.repo.AlbumRepo;
import lk.ijse.pahasarastudiospringfinal.repo.ClientRepo;
import lk.ijse.pahasarastudiospringfinal.service.AlbumService;
import lk.ijse.pahasarastudiospringfinal.util.MappingUtil;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepo albumRepo;
    private final ClientRepo clientRepo;
    private final MappingUtil mappingUtil;

    public AlbumServiceImpl(AlbumRepo albumRepo,
                            ClientRepo clientRepo,
                            MappingUtil mappingUtil) {
        this.albumRepo = albumRepo;
        this.clientRepo = clientRepo;
        this.mappingUtil = mappingUtil;
    }

    // ================= SAVE =================
    @Override
    public String saveAlbum(AlbumDTO dto) {

        Client client = clientRepo.findById(dto.getClientId()).orElse(null);

        if (client == null) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        Album album = mappingUtil.toAlbumEntity(dto, client);
        albumRepo.save(album);

        return VarList.RSP_SUCCESS;
    }

    // ================= UPDATE =================
    @Override
    public String updateAlbum(AlbumDTO dto) {

        Album existing = albumRepo.findById(dto.getId()).orElse(null);
        if (existing == null) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        Client client = clientRepo.findById(dto.getClientId()).orElse(null);
        if (client == null) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        // IMPORTANT → Update existing entity
        existing.setAlbumName(dto.getAlbumName());
        existing.setAlbumType(dto.getAlbumType());
        existing.setPrice(dto.getPrice());
        existing.setStatus(dto.getStatus());
        existing.setClient(client);

        albumRepo.save(existing);

        return VarList.RSP_SUCCESS;
    }

    // ================= DELETE =================
    @Override
    public String deleteAlbum(Long id) {

        Album album = albumRepo.findById(id).orElse(null);

        if (album == null) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        albumRepo.delete(album);

        return VarList.RSP_SUCCESS;
    }

    // ================= GET ALL =================
    @Override
    public List<AlbumDTO> getAllAlbums() {

        return albumRepo.findAll()
                .stream()
                .map(mappingUtil::toAlbumDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY CLIENT =================
    @Override
    public List<AlbumDTO> getAlbumsByClientId(Long clientId) {

        return albumRepo.findByClientId(clientId)
                .stream()
                .map(mappingUtil::toAlbumDTO)
                .collect(Collectors.toList());
    }
}
