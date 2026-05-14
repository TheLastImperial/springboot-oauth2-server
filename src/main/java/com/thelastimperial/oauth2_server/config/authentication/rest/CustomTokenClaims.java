package com.thelastimperial.oauth2_server.config.authentication.rest;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public interface CustomTokenClaims {
    JwtClaimsSet.Builder addCustomClaims(JwtClaimsSet.Builder builder);
}
