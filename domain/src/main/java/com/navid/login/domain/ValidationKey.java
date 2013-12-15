package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class ValidationKey implements Serializable {
    
    private final Token token;
    
    private final String verificationCode;
    
    public ValidationKey(Token token, String verificationCode ) {
        this.token = token;
        this.verificationCode = verificationCode;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the verificationCode
     */
    public String getVerificationCode() {
        return verificationCode;
    }
    
    
}
