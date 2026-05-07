package com.thelastimperial.oauth2_server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.oauth2_server.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    public Optional<UserEntity> findByEmailOrUsername(String email, String username);
}
