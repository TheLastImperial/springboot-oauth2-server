package com.thelastimperial.oauth2_server.config.authentication.rest;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    private static final JacksonJsonHttpMessageConverter messageConverter =
        new JacksonJsonHttpMessageConverter();
    private Clock clock = Clock.systemUTC();
    private final JwtEncoder jwtEncoder;
    private CustomTokenClaims customTokenClaims;

    public RestAuthenticationSuccessHandler(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }

    public RestAuthenticationSuccessHandler(JwtEncoder jwtEncoder,
        CustomTokenClaims customTokenClaims
    ){
        this.jwtEncoder = jwtEncoder;
        this.customTokenClaims = customTokenClaims;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {
        Instant issuedAt = this.clock.instant();
		Instant expiresAt = issuedAt.plus(30, ChronoUnit.MINUTES);
        
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(request.getRequestURL().toString())
				.subject(authentication.getName())
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.id(UUID.randomUUID().toString());
        
        if(customTokenClaims != null)
            claimsBuilder = customTokenClaims.addCustomClaims(claimsBuilder);

        JwsHeader.Builder jwsHeaderBuilder = JwsHeader.with(SignatureAlgorithm.RS256);

        OAuth2Token token = this.jwtEncoder.encode(
            JwtEncoderParameters.from(jwsHeaderBuilder.build(), claimsBuilder.build())
        );

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        messageConverter.write(token, MediaType.APPLICATION_JSON, httpResponse);
    }
}
