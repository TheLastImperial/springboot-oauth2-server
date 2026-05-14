package com.thelastimperial.oauth2_server.config.authentication.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.thelastimperial.oauth2_server.services.UserService;

@Configuration
public class RestAuthenticationConfig {
    @Bean
    public RestAuthenticationFilter getUsernamePasswordAuthenticationFilter(
        AuthenticationManager authenticationManager,
        JWKSource<SecurityContext> jwks,
        CustomTokenClaims customTokenClaims
    ){
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwks);
        return new RestAuthenticationFilter(
            authenticationManager, jwtEncoder, customTokenClaims
        );
    }

    @Bean
    public CustomTokenClaims tokenClaimsGenerator(UserService userService){
        return new UserTokenClaims(userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(
        DaoAuthenticationProvider daoAuthenticationProvider
    ){
        return new ProviderManager(daoAuthenticationProvider);
    }
}
