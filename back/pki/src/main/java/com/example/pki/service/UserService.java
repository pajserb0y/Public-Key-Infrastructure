package com.example.pki.service;

import com.example.pki.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> save(User user);
}
