package lk.ijse.pahasarastudiospringfinal.service;

import lk.ijse.pahasarastudiospringfinal.dto.EmailDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface EmailService {
    void sendEmailWithAttachments(String to, String subject, String message, MultipartFile[] files);
    List<EmailDTO> getAllEmails();
}