package com.thelastimperial.oauth2_server.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thelastimperial.oauth2_server.entities.UserEntity;
import com.thelastimperial.oauth2_server.repositories.UserRepository;
import com.thelastimperial.oauth2_server.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserEntity get(UUID id) {
        return userRepository
            .findById(id)
            // TODO: Add exception when not found User
            .get();
    }
}
