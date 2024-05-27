package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.Models.PasswordResetToken;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Repos.TokenRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceIMPL {

    private final JavaMailSender mailSender;

    private final TokenRepo tokenRepo;


    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Koriander@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public String sendForgotPasswordEmail(User user) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            String resetLink = generateResetToken(user);
            String message = String.format("""
                    Hello!
                   
                    Please click on this link to reset your password: %s.
                    
                    Regards Koriander crew""", resetLink);

            email.setFrom("Koriander@gmail.com");
            email.setTo(user.getUsername());
            email.setSubject("Password Recovery");
            email.setText(message);
            mailSender.send(email);
            System.out.println("success");
            return "success";
        } catch (Exception e) {
            System.out.println("Email not found");
            return "error";
        }
    }

    public void sendEmailHTML(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom("koriander@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expireTime = currentTime.plusMinutes(5);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .user(user)
                .token(uuid.toString())
                .expiryDate(expireTime)
                .build();

        tokenRepo.save(resetToken);

        String endpointUrl = "http://localhost:8080/resetPassword";
        return endpointUrl + "/" + resetToken.getToken();
    }


    public boolean hasExpired(LocalDateTime expireDate) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expireDate);
    }



}
