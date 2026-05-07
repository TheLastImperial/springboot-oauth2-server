package com.thelastimperial.oauth2_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;

@Configuration
public class RegisteredClientConfig {
    @Bean
    public JdbcRegisteredClientRepository getRegisteredClientRepository(
        JdbcOperations jdbcOperations
    ){
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }
}
