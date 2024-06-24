package org.abbtech.practice.service.impl;

import lombok.RequiredArgsConstructor;
import org.abbtech.practice.dto.CustomUserDetailDto;
import org.abbtech.practice.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userInfo = userRepository.findUserInfoByUsernameIgnoreCase(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(401)));
        return new CustomUserDetailDto( userInfo);
}
}
