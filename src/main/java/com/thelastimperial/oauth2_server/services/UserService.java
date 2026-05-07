package com.thelastimperial.oauth2_server.services;

import java.util.UUID;

import com.thelastimperial.oauth2_server.entities.UserEntity;

public interface UserService {
    public UserEntity get(UUID id);
}
