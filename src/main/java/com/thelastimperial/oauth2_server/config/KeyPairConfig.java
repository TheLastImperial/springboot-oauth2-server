package com.thelastimperial.oauth2_server.config;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyPairConfig {
    private String publicKey;
    private String privateKey;
    
    public KeyPairConfig(
        @Value("${com.thelastimperial.oauth2_server.keypair.public}")
        String publicKey,
        @Value("${com.thelastimperial.oauth2_server.keypair.private}")
        String privateKey
    ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Bean
    public KeyPair getKeyPair() throws Exception {
        return new KeyPair(getPublicKey(), getPrivateKey());
    }

    private PublicKey getPublicKey() throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory publicKf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = publicKf.generatePublic(publicSpec);
        return publicKey;
    }

    private PrivateKey getPrivateKey() throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory privateKf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = privateKf.generatePrivate(privateSpec);
        return privateKey;
    }
}
