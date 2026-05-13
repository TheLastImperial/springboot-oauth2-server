package com.thelastimperial.oauth2_server.config.authentication.rest;

import java.io.IOException;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.thelastimperial.oauth2_server.controllers.rq.LoginRq;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    private static final HttpMessageConverter<LoginRq> messageConverter =
        new RestLoginMessageConverter();

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {

        LoginRq login = new LoginRq();
        login.setUsername(authentication.getName());

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        messageConverter.write(login, null, httpResponse);
    }
}
