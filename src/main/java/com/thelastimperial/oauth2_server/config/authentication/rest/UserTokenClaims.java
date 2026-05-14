package com.thelastimperial.oauth2_server.config.authentication.rest;

import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtClaimsSet.Builder;

import com.thelastimperial.oauth2_server.entities.RoleEntity;
import com.thelastimperial.oauth2_server.entities.UserEntity;
import com.thelastimperial.oauth2_server.services.UserService;

public class UserTokenClaims implements CustomTokenClaims {
    private final UserService userService;

    public UserTokenClaims(UserService userService){
        this.userService = userService;
    }

    @Override
    public Builder addCustomClaims(Builder builder) {
        JwtClaimsSet claims = builder.build();
        UserEntity user = userService.getByUsername(claims.getSubject());
        builder.claim("email", user.getEmail());
        builder.claim(
            "roles",
            user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList())
        );

        return builder;
    }

}
