package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/send", consumes = {"multipart/form-data"})
    public ResponseEntity<String> sendEmail(
            @RequestParam("toEmail") String toEmail,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        try {
            emailService.sendEmailWithAttachments(toEmail, subject, message, files);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmails() {
        return ResponseEntity.ok(emailService.getAllEmails());
    }
}