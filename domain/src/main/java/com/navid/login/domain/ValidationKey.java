package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class ValidationKey implements Serializable {
    
    private final Token token;
    
    private final String validationKey;
    
    public ValidationKey(Token token, String validationKey ) {
        this.token = token;
        this.validationKey = validationKey;
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
    public String getValidationKey() {
        return validationKey;
    }
    
    
}
