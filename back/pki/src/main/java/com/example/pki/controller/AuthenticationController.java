package com.example.pki.controller;

import com.example.pki.model.User;
import com.example.pki.model.dto.UserCredentials;
import com.example.pki.model.dto.UserDTO;
import com.example.pki.model.dto.UserTokenDTO;
import com.example.pki.security.tokenUtils.JwtTokenUtils;
import com.example.pki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final JwtTokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationController(JwtTokenUtils tokenUtils, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se AuthenticationException
        Authentication auth;
        System.out.println(userCredentials.getEmail());
        System.out.println(userCredentials.getPassword());
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail(), user.getRole());
        System.out.println(jwt);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
        return userService.save(userDto);
    }
}