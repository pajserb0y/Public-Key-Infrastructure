package com.example.pki.service;

import com.example.pki.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> save(UserDTO userDto);
}
