package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.AlbumDTO;
import java.util.List;

public interface AlbumService {
    String saveAlbum(AlbumDTO albumDTO);
    String updateAlbum(AlbumDTO albumDTO);
    String deleteAlbum(Long id);
    List<AlbumDTO> getAllAlbums();
    List<AlbumDTO> getAlbumsByClientId(Long clientId);
}