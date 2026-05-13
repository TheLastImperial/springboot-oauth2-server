package com.thelastimperial.oauth2_server.config.authentication.rest;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

import com.thelastimperial.oauth2_server.controllers.rq.LoginRq;

public class RestLoginMessageConverter extends AbstractHttpMessageConverter<LoginRq> {
    private final JacksonJsonHttpMessageConverter jsonMessageConverter =
        new JacksonJsonHttpMessageConverter();
    @Override
    protected boolean supports(Class<?> clazz) {
        return LoginRq.class.isAssignableFrom(clazz);
    }

    @Override
    protected LoginRq readInternal(Class<? extends LoginRq> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        LoginRq login = new LoginRq();
        login.setUsername("USERNAME");
        login.setPassword("PASSWORD");
        return login;
    }

    @Override
    protected void writeInternal(LoginRq t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        jsonMessageConverter.write(t, null, outputMessage);
    }
    
}
