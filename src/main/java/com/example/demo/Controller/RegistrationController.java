package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;

@RestController
public class RegistrationController {
    
    @Autowired
    private MyAppUserRepository myAppUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody MyAppUser user) {

        if (myAppUserRepository.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already taken.", HttpStatus.BAD_REQUEST);
        }
        if (myAppUserRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already registered.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(true);
        user.setVerificationToken(null);
        myAppUserRepository.save(user);

        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }
}
