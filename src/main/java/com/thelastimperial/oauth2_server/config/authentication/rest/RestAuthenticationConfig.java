package com.thelastimperial.oauth2_server.config.authentication.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class RestAuthenticationConfig {
    @Bean
    public RestAuthenticationFilter getUsernamePasswordAuthenticationFilter(
        AuthenticationManager authenticationManager
    ){
        return new RestAuthenticationFilter(
            authenticationManager
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(
        DaoAuthenticationProvider daoAuthenticationProvider
    ){
        return new ProviderManager(daoAuthenticationProvider);
    }
}
