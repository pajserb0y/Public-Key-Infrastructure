package com.example.pki.service;

import com.example.pki.model.Role;
import com.example.pki.model.User;
import com.example.pki.model.dto.UserDTO;
import com.example.pki.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    EmailService emailService;


    @Override
    public ResponseEntity<?> save(UserDTO dto , HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            User user = new User(dto);
            user.setSalt(RandomStringInitializer.generateAlphaNumericString(10));
            user.setPassword(passwordEncoder.encode(user.getPassword().concat(user.getSalt())));
            String pin = RandomStringInitializer.generatePin();
            user.setPin(passwordEncoder.encode(pin.concat(user.getSalt())));
            user.setActivated(false);
            user.setForgotten(0);
            user.setMissedPasswordCounter(0);
            Role role = roleService.findByName("ROLE_INTER_USER");
            user.setRole(role);
            userRepository.save(user);
            emailService.sendActivationMailClientAsync(findByEmail(user.getUsername()));
            emailService.sendPin(user.getEmail(), pin);
            log.info("Ip: {}, username: {}, Client successfully created!", request.getRemoteAddr(), user.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            log.error("Ip: {}, Client not created! Already exist user with same username or email.", request.getRemoteAddr(), e);
            return new ResponseEntity<>("Already exist user with same username or email", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean isPinOk(String email, String pin) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        Calendar c = Calendar.getInstance();
        c.setTime(user.getPinCreatedDate());
        c.add(Calendar.MINUTE, 1);

        if (user.getPin().equals("") || c.getTime().before(new Date()))
            return false;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String saltedPin = pin.concat(user.getSalt());
        boolean match = passwordEncoder.matches(saltedPin, user.getPin());
        return match;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Collection<String> findAllEmails() {
        return userRepository.findAllEmails();
    }

    @Override
    public ResponseEntity<?> isPasswordInBlackList(String pass) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("PasswordBlackList.txt").toURI());
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        List<String> passwords = Arrays.asList(data.split("\n"));
        if (passwords.contains(pass)){
            log.warn("Password is blacklisted");
            return new ResponseEntity<>("Your password has been compromised. Please enter new password.", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> sendNewPassword(User client, HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = RandomStringInitializer.generateAlphaNumericString(10);
        client.setPassword(passwordEncoder.encode(password.concat(client.getSalt())));
        client.setForgotten(1);
        client.setPin(RandomStringInitializer.generatePin());
        saveUser(client);
        emailService.sendNewPassword(client.getEmail(), password);
        //emailService.sendPin(client.getEmail(), client.getPin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(User client ,HttpServletRequest request) {

        User clientInDb = findByEmail(client.getUsername());
        clientInDb.setEmail(client.getEmail());
        clientInDb.setFirstName(client.getFirstName());
        clientInDb.setLastName(client.getLastName());
        if (!clientInDb.getPassword().equals(client.getPassword()) || client.getPassword() == "") {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            clientInDb.setPassword(passwordEncoder.encode(client.getPassword().concat(clientInDb.getSalt())));
            clientInDb.setForgotten(0);
            String pin = RandomStringInitializer.generatePin();
            clientInDb.setPin(passwordEncoder.encode(pin.concat(clientInDb.getSalt())));
        }
        saveUser(clientInDb);
        log.info("Ip: {}, username: {}, User updated successfully!", request.getRemoteAddr(), clientInDb.getUsername());
        return new ResponseEntity<>(clientInDb, HttpStatus.OK);
    }

    @Override
    public void send2factorAuthPin(User user, HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pin = RandomStringInitializer.generatePin();
        user.setPin(passwordEncoder.encode(pin.concat(user.getSalt())));
        user.setPinCreatedDate(new Date());
        userRepository.save(user);
        emailService.send2factorAuthPin(user.getEmail(), pin);
    }
}
