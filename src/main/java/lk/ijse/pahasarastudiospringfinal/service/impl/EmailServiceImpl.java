package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.EmailDTO;
import lk.ijse.pahasarastudiospringfinal.entity.Email;
import lk.ijse.pahasarastudiospringfinal.repo.EmailRepo;
import lk.ijse.pahasarastudiospringfinal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepo emailRepo;

    @Override
    public void sendEmail(EmailDTO dto) {
        // Save to DB
        Email email = new Email();
        email.setToEmail(dto.getToEmail());
        email.setSubject(dto.getSubject());
        email.setMessage(dto.getMessage());
        emailRepo.save(email);

        // Send Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getToEmail());
        message.setSubject(dto.getSubject());
        message.setText(dto.getMessage());
        mailSender.send(message);
    }

    @Override
    public List<EmailDTO> getAllEmails() {
        return emailRepo.findAll().stream()
                .map(email -> new EmailDTO(email.getId(), email.getToEmail(), email.getSubject(), email.getMessage()))
                .toList();
    }
}
