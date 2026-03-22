package lk.ijse.pahasarastudiospringfinal.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
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
    @Transactional // Ensures database and email send are consistent
    public void sendEmailWithAttachments(String to, String subject, String message, MultipartFile[] files) {
        try {
            // 1. Construct MimeMessage for attachments
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 'true' indicates multipart mode is required for attachments
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            // 2. Process Attachments
            if (files != null) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // helper.addAttachment handles the input stream and filename
                        helper.addAttachment(file.getOriginalFilename(), file);
                    }
                }
            }

            // 3. Send Email
            mailSender.send(mimeMessage);

            // 4. Save to Database ONLY after successful send
            Email emailEntity = new Email();
            emailEntity.setToEmail(to);
            emailEntity.setSubject(subject);
            emailEntity.setMessage(message);
            emailRepo.save(emailEntity);

        } catch (MessagingException e) {
            throw new RuntimeException("Email construction failed: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<EmailDTO> getAllEmails() {
        return emailRepo.findAll().stream()
                .map(e -> new EmailDTO(e.getId(), e.getToEmail(), e.getSubject(), e.getMessage()))
                .toList();
    }

}