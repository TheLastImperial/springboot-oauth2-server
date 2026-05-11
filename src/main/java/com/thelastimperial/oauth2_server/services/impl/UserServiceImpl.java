package com.thelastimperial.oauth2_server.services.impl;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thelastimperial.oauth2_server.entities.UserEntity;
import com.thelastimperial.oauth2_server.repositories.UserRepository;
import com.thelastimperial.oauth2_server.services.UserService;
import com.thelastimperial.oauth2_server.utils.ValidationHelper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserEntity get(UUID id) {
        return userRepository
            .findById(id)
            // TODO: Add exception when not found User
            .get();
    }

    public UserEntity getByUsername(String username) throws UsernameNotFoundException{
        UserEntity user = new UserEntity();
        String emailPatter = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@"+
            "[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";

        if(ValidationHelper.isUUID(username)){
            user = userRepository
                .findById(UUID.fromString(username))
                .orElseThrow(()-> new UsernameNotFoundException(username));
        }else if(Pattern.compile(emailPatter).matcher(username).matches()){
            user = userRepository
                .findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        }else{
            user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        }

        return user;
    }
}
