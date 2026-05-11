package com.thelastimperial.oauth2_server.utils;

import java.util.UUID;

public class ValidationHelper {
    public static boolean isUUID(String uuid){
        try{
            UUID.fromString(uuid);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
}
