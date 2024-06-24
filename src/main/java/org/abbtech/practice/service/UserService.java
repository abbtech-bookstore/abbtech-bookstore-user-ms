package org.abbtech.practice.service;

import org.abbtech.practice.dto.UserRequestDTO;

public interface UserService {
    String addUser(UserRequestDTO userRequest);
    String generateToken(String username);
    void validateToken(String token);
}
