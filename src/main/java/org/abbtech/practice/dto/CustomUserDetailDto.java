package org.abbtech.practice.dto;

import org.abbtech.practice.model.User;
import org.abbtech.practice.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetailDto implements UserDetails {

    private final String username;
    private final String password;
    private final Set<? extends GrantedAuthority> authorities;


    public CustomUserDetailDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        Set<GrantedAuthority> auths = new HashSet<>();
        for (UserRole role : user.getUserRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
        }
        this.authorities=auths;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
