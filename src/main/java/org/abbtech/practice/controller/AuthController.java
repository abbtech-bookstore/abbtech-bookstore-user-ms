package org.abbtech.practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abbtech.practice.dto.AuthenticationDto;
import org.abbtech.practice.dto.AuthenticationDto;
import org.abbtech.practice.dto.UserRequestDTO;
import org.abbtech.practice.exception.UserNotFoundException;
import org.abbtech.practice.model.User;
import org.abbtech.practice.repository.UserRepository;
import org.abbtech.practice.service.JWTService;
import org.abbtech.practice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.abbtech.practice.exception.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private UserRepository userRepository;
    private UserService userService;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.OK);
    }


    @PostMapping("/login")
    public String getToken(@RequestBody AuthenticationDto authDTO) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return userService.generateToken(authDTO.getUsername());
        } else throw new UserNotFoundException("no such user registered");
    }

    public ResponseEntity<String> validateToken(@RequestBody String token) {
        try {
            userService.validateToken(token);
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token is invalid", HttpStatus.BAD_REQUEST);
        }

    }


}
