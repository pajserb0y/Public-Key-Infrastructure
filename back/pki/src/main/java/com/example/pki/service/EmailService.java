package com.example.pki.service;

import com.example.pki.model.User;
import com.example.pki.model.ConfirmationToken;
import com.example.pki.repository.ConfirmationTokenRepository;
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

        javaMailSender.send(mail);
    }


    @Async
    public void sendNewPassword(String email, String password) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Refreshed password");
        mail.setText("Your new password is: " + password + ".\nYou have to set your password when you first log in.");

        javaMailSender.send(mail);
    }

    @Async
    public void sendPin(String email, String pin) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("New login PIN");
        mail.setText("Your new PIN is: " + pin);

        javaMailSender.send(mail);
    }

    public void send2factorAuthPin(String email, String pin) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("2 factor authentication PIN");
        mail.setText("Your PIN is: " + pin);

        javaMailSender.send(mail);
    }
}
