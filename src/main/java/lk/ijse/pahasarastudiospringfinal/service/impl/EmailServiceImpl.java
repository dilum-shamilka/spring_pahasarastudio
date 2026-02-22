package lk.ijse.pahasarastudiospringfinal.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.pahasarastudiospringfinal.dto.EmailDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Email;
import lk.ijse.pahasarastudiospringfinal.repo.EmailRepo;
import lk.ijse.pahasarastudiospringfinal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepo emailRepo;

    @Override
    public void sendEmailWithAttachments(String to, String subject, String message, MultipartFile[] files) {
        try {
            // 1. Save to Database first
            Email emailEntity = new Email();
            emailEntity.setToEmail(to);
            emailEntity.setSubject(subject);
            emailEntity.setMessage(message);
            emailRepo.save(emailEntity);

            // 2. Prepare Email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            // 3. Attach files if they exist
            if (files != null) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        helper.addAttachment(file.getOriginalFilename(), file);
                    }
                }
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage());
        }
    }

    @Override
    public List<EmailDTO> getAllEmails() {
        return emailRepo.findAll().stream()
                .map(e -> new EmailDTO(e.getId(), e.getToEmail(), e.getSubject(), e.getMessage()))
                .toList();
    }
}