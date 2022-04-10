package com.example.pki.service;

import com.example.pki.model.User;
import com.example.pki.model.dto.UserDTO;
import com.example.pki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;


    @Override
    public ResponseEntity<?> save(UserDTO userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));    //msm da se to vec radi u WebSecurityConfig
        User user = new User(userDto);
        user.setRole(roleService.findByName("ROLE_USER"));
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
