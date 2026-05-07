package com.thelastimperial.oauth2_server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thelastimperial.oauth2_server.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID>{
    
}
