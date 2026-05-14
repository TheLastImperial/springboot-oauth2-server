package com.thelastimperial.oauth2_server.config.authentication.rest;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.thelastimperial.oauth2_server.controllers.rq.LoginRq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final RequestMatcher DEFAULT_PATH_REQUEST_MATCHER = PathPatternRequestMatcher
        .withDefaults()
		.matcher(HttpMethod.POST, "/login");
    private static final MediaTypeRequestMatcher jsonMatcher = new MediaTypeRequestMatcher(
        new HeaderContentNegotiationStrategy(), 
        MediaType.APPLICATION_JSON
    );

    public RestAuthenticationFilter(
        AuthenticationManager authenticationManager, JwtEncoder jwtEncoder
    ){
        super(
            new AndRequestMatcher(DEFAULT_PATH_REQUEST_MATCHER, jsonMatcher), authenticationManager
        );
        RestAuthenticationSuccessHandler successHandler = new RestAuthenticationSuccessHandler(
            jwtEncoder
        );

        super.setAuthenticationSuccessHandler(successHandler);
    }

    public RestAuthenticationFilter(
        AuthenticationManager authenticationManager, JwtEncoder jwtEncoder,
        CustomTokenClaims customTokenClaims
    ){
        super(
            new AndRequestMatcher(DEFAULT_PATH_REQUEST_MATCHER, jsonMatcher), authenticationManager
        );
        RestAuthenticationSuccessHandler successHandler = new RestAuthenticationSuccessHandler(
            jwtEncoder, customTokenClaims
        );

        super.setAuthenticationSuccessHandler(successHandler);
    }
    @Override
	public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) {
        ObjectMapper mapper = new ObjectMapper();
        LoginRq login;
        try{
            login = mapper.readValue(request.getReader(), LoginRq.class);
        }catch(Exception ex){
            throw new AuthenticationServiceException(
                "Invalid JSON body."
            );
        }

		String username = login.getUsername();
		username = (username != null) ? username.trim() : "";
		String password = login.getPassword();
		password = (password != null) ? password : "";
		UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
            .unauthenticated(username,password);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

    public AndRequestMatcher getAndRequestMatcher(){
        return new AndRequestMatcher(DEFAULT_PATH_REQUEST_MATCHER, jsonMatcher);
    }
}
