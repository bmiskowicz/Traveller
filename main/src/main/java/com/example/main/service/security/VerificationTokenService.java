package com.example.main.service.security;

import com.example.main.entity.log.VerificationToken;
import com.example.main.repository.log.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Component
public class VerificationTokenService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public ResponseEntity<String> sendVerificationToken(String email){

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(email, token);
        verificationTokenRepository.save(verificationToken);


        String subject = "Registration Confirmation";
        String confirmationUrl = "/registrationConfirm/" + token;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText("Verification on page" + "\r\n" + "https://localhost:442" + confirmationUrl);
        mailSender.send(simpleMailMessage);

        return ResponseEntity.ok("Verification mail send on email: " + email);
    }
}
