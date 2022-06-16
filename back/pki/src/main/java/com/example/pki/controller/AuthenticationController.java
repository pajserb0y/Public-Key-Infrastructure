package com.example.pki.controller;

import com.example.pki.model.ConfirmationToken;
import com.example.pki.model.Role;
import com.example.pki.model.User;
import com.example.pki.model.dto.EmailDto;
import com.example.pki.model.dto.UserCredentials;
import com.example.pki.model.dto.UserDTO;
import com.example.pki.model.dto.UserTokenDTO;
import com.example.pki.repository.ConfirmationTokenRepository;
import com.example.pki.security.tokenUtils.JwtTokenUtils;
import com.example.pki.service.EmailService;
import com.example.pki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final JwtTokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    @Autowired
    EmailService emailService;

    @Autowired
    public AuthenticationController(JwtTokenUtils tokenUtils, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;


    @PostMapping("/login")
    public String login(@RequestBody UserCredentials authenticationRequest) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se AuthenticationException
        if(isUserBlocked(authenticationRequest.getEmail()))
            return "Your account is currently blocked. Try next day again.";
        String salt = findSaltForUsername(authenticationRequest.getEmail());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword().concat(salt)));
            if(userService.isPinOk(authenticationRequest.getEmail(), authenticationRequest.getPin())) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                refreshMissedPasswordCounter(authenticationRequest.getEmail());
            }
            else {
                increaseMissedPasswordCounter(authenticationRequest.getEmail());
                return "Invalid pin.";
            }
        } catch (AuthenticationException e) {
            increaseMissedPasswordCounter(authenticationRequest.getEmail());
            return "Invalid username, password or pin.";
        }

        User user;
        if(authentication == null)
            user = userService.findByEmail(authenticationRequest.getEmail());
        else
            user = (User) authentication.getPrincipal();
        if (user.getForgotten() == 1) {
            user.setForgotten(2);
            userService.saveUser(user);
        }
        else if (user.getForgotten() == 2)
            return "You did not changed password first time. If you want to log in, refresh again your password.";
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole());

        return jwt;
    }

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDto, BindingResult res) {
        if(res.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return userService.save(userDto);
    }

    private boolean isUserBlocked(String email) {
        User user = userService.findByEmail(email);
        if(user != null && user.getBlockedDate() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(user.getBlockedDate());
            c.add(Calendar.DATE, 1);
            if (user.isBlocked() && c.getTime().after(new Date()))
                return true;
            else if (user.isBlocked() && c.getTime().before(new Date())) {
                user.setBlocked(false);
                user.setMissedPasswordCounter(0);
                userService.saveUser(user);
                return false;
            }
        }

        return false;
    }

    private void increaseMissedPasswordCounter(String email) {
        User user = userService.findByEmail(email);
        if(user != null) {
            user.setMissedPasswordCounter(user.getMissedPasswordCounter() + 1);
            if (user.getMissedPasswordCounter() > 5) {
                user.setBlocked(true);
                user.setBlockedDate(new Date());
            }
            userService.saveUser(user);
        }
    }

    private void refreshMissedPasswordCounter(String email) {
        User user = userService.findByEmail(email);
        if(user != null) {
            user.setMissedPasswordCounter(0);
            user.setPin("");
            userService.saveUser(user);
        }
    }

    private String findSaltForUsername(String email) {
        if(userService.findByEmail(email) != null)
            return userService.findByEmail(email).getSalt();
        return "";
    }

    @GetMapping(path = "/getAllUsernames")
    public Set<String> getAllUsername() {
        Set<String> usernameList = new HashSet<>();
        usernameList.addAll(userService.findAllEmails());

        return usernameList;
    }

    @GetMapping(path = "/password/blackList/{pass}")
    public ResponseEntity<?> checkPasswordBlackList(@PathVariable String pass) throws URISyntaxException, IOException {
        return userService.isPasswordInBlackList(pass);
    }

    @GetMapping(path = "/activate")
    public ResponseEntity<?> activateClientAccount(WebRequest request, @RequestParam("token") String hashCode) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(hashCode);
        Long secs = (token.getCreatedDate().getTime() - new Date().getTime())/1000;
        User verificationClient = token.getUser();
        if (verificationClient == null || verificationClient.isActivated() || secs > 3600 ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        verificationClient.setActivated(true);
        userService.saveUser(verificationClient);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "https://localhost:4100");
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @PutMapping(path = "/newPassword")
    public ResponseEntity<?> sendNewPassword(@RequestBody EmailDto dto) {
        if(userService.findByEmail(dto.getEmail()) != null)
            return userService.sendNewPassword(userService.findByEmail(dto.getEmail()));
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('userByEmail')")
    @GetMapping(path = "/email/{email}")
    public ResponseEntity<?> userByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('updateUser')")
    @PostMapping(path = "/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO client) {
        return userService.update(new User(client));
    }

    @GetMapping(path = "/sso/{username}")
    public ResponseEntity<?> sendPasswordlessToken(@PathVariable String username) {
        User user = userService.findByEmail(username);
        if(user != null)
            return emailService.sendPasswordless(user.getEmail(), tokenUtils.generateToken(username, null));

        return new ResponseEntity<>("User with that username does not exist.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/login/passwordless")
    public String loginPaswordless(@RequestParam("token") String token) {
        String username = tokenUtils.getUsernameFromToken(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = "";
        User user = userService.findByEmail(username);
        if(user != null)
            jwt = tokenUtils.generateToken(user.getUsername(), user.getRole());

        return jwt;
    }

    @PostMapping(path = "/2factorAuth/pin/send")
    public ResponseEntity<?> sendPinFor2Auth(@RequestBody UserCredentials authenticationRequest) {
        User user = userService.findByEmail(authenticationRequest.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(user != null) {
            if(passwordEncoder.matches(authenticationRequest.getPassword().concat(user.getSalt()), user.getPassword())) {
                userService.send2factorAuthPin(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                increaseMissedPasswordCounter(authenticationRequest.getEmail());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}