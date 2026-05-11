package com.thelastimperial.oauth2_server.services;

import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.thelastimperial.oauth2_server.entities.UserEntity;

public interface UserService {
    public UserEntity get(UUID id);
    public UserEntity getByUsername(String username) throws UsernameNotFoundException;
}
