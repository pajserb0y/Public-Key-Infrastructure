package com.example.pki.service;

import com.example.pki.model.User;
import com.example.pki.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface UserService {
    ResponseEntity<?> save(UserDTO userDto);

    boolean isPinOk(String email, String pin);

    User findByEmail(String email);

    void saveUser(User user);

    Collection<String> findAllEmails();

    ResponseEntity<?> isPasswordInBlackList(String pass) throws URISyntaxException, IOException;
}
