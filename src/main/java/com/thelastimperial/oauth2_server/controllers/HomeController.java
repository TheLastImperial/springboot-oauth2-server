package com.thelastimperial.oauth2_server.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomeController {
    @GetMapping("/")
    public String getIndex() {
        return "Pagina de inicio";
    }

    @GetMapping("/user")
    public String getUser() {
        return "User page";
    }
    
    
}
