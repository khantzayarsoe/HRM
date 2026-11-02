package com.humanresource.humanresourcemanagement.services;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class PasswordEncoder {

    public static String encode(String rawPassword){
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        return hashed;
    }

    public static boolean isMatched(String rawPassword, String encoded){
        boolean valid = BCrypt.checkpw(rawPassword, encoded);
        return valid;
    }

}
