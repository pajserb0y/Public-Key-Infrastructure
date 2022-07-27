package com.example.pki.service;

import com.example.pki.model.User;
import com.example.pki.model.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface UserService {
    ResponseEntity<?> save(UserDTO userDto, HttpServletRequest request);

    boolean isPinOk(String email, String pin);

    User findByEmail(String email);

    void saveUser(User user);

    Collection<String> findAllEmails();

    ResponseEntity<?> isPasswordInBlackList(String pass) throws URISyntaxException, IOException;

    ResponseEntity<?> sendNewPassword(User client, HttpServletRequest request);

    ResponseEntity<?> update(User client, HttpServletRequest request);

    void send2factorAuthPin(User user, HttpServletRequest request);
}
