package com.thelastimperial.oauth2_server.controllers.rq;

import lombok.Data;

@Data
public class LoginRq {
    private String username;
    private String password;
}
