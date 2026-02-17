package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.EmailDTO;
import java.util.List;

public interface EmailService {
    void sendEmail(EmailDTO emailDTO);
    List<EmailDTO> getAllEmails();
}
