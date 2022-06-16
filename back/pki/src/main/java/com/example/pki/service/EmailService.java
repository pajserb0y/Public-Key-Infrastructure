package com.example.pki.service;

import com.example.pki.model.User;
import com.example.pki.model.ConfirmationToken;
import com.example.pki.repository.ConfirmationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;


    @Async
    public void sendActivationMailClientAsync(User user) throws MailException {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Activation mail");
        mail.setText("Hi, " + user.getFirstName() + ".\n\nWelcome to our site." +
                "\nWe hope that you will be satisfied with our services." +
                "\nIn order to activate your account click on this link: " +
                "https://localhost:8080/auth/activate?token=" + confirmationToken.getConfirmationToken());
        log.info("Username: {}, Activaton email sent!", user.getUsername());
        javaMailSender.send(mail);
    }


    @Async
    public void sendNewPassword(String email, String password) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Refreshed password");
        mail.setText("Your new password is: " + password + ".\nYou have to set your password when you first log in.");

        log.info("Email: {}, New password sent!", email);
        javaMailSender.send(mail);
    }

    @Async
    public void sendPin(String email, String pin) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("New login PIN");
        mail.setText("Your new PIN is: " + pin);

        log.info("Email: {}, New pin sent!", email);
        javaMailSender.send(mail);
    }

    @Async
    public void send2factorAuthPin(String email, String pin) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("2 factor authentication PIN");
        mail.setText("Your PIN is: " + pin);

        log.info("Email: {}, 2 factor auth pin sent!", email);
        javaMailSender.send(mail);
    }

    @Async
    public ResponseEntity<?> sendPasswordless(String email, String salt) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Passwordless authentication");
        mail.setText("In order to login click on this link: " +
                "https://localhost:4100/passwordless?token=" + salt);

        log.info("Email: {}, passwordless activation sent!", email);
        javaMailSender.send(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
