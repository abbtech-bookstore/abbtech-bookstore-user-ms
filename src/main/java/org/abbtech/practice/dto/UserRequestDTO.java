package org.abbtech.practice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private List<String> userRoles;
}
