package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class User implements Serializable {
    
    private final String email;
    
    public User(String email){
        this.email = email;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
}
