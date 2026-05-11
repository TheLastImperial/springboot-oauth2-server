package com.thelastimperial.oauth2_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.web.webauthn.management.JdbcPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.JdbcUserCredentialRepository;

@Configuration
public class KeyPassConfig {

    @Bean
    public JdbcUserCredentialRepository getjJdbcUserCredentialRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcUserCredentialRepository(jdbcOperations);
    }

    @Bean
    public JdbcPublicKeyCredentialUserEntityRepository getJdbcPublicKeyCredentialUserEntityRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcPublicKeyCredentialUserEntityRepository(jdbcOperations);
    }
}
