package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.PhotoFrameDTO;
import java.util.List;

public interface PhotoFrameService {

    PhotoFrameDTO saveFrame(PhotoFrameDTO dto);

    void updateFrame(Long id, PhotoFrameDTO dto);

    void deleteFrame(Long id);

    PhotoFrameDTO getFrame(Long id);

    List<PhotoFrameDTO> getAllFrames();
}
