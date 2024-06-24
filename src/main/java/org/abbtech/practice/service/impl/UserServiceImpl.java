package org.abbtech.practice.service.impl;

import lombok.RequiredArgsConstructor;
import org.abbtech.practice.dto.UserRequestDTO;
import org.abbtech.practice.exception.EmailAlreadyExistsException;
import org.abbtech.practice.exception.UserNotFoundException;
import org.abbtech.practice.model.User;
import org.abbtech.practice.model.UserRole;
import org.abbtech.practice.repository.UserRepository;
import org.abbtech.practice.service.JWTService;
import org.abbtech.practice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.abbtech.practice.exception.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Override
    public String addUser(UserRequestDTO userRequest) {
        if (!userRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()).isPresent()) {
            var userInfo = User.builder()
                    .email(userRequest.getEmail())
                    .username(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .build();
            userInfo.setUserRoles(userRequest.getUserRoles().stream()
                    .map(role -> UserRole.builder()
                            .user(userInfo)
                            .role(role).build())
                    .collect(Collectors.toList()));

            userRepository.save(userInfo);
            return jwtService.generateToken(Optional.of(userInfo));
        }
        throw new EmailAlreadyExistsException("Provided email already registered to the system!");
    }

    @Override
    public String generateToken(String username) {
        User user = userRepository.findUserInfoByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("No such user"));
        return jwtService.generateToken(Optional.ofNullable(user));
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
