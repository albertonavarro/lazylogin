package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class Token implements Serializable {

    private final User user;

    private final Long value;

    private Boolean validated;
    
    /**
     * 
     * @param user
     * @param value
     * @param validated
     */
    public Token(User user, Long value, Boolean validated) {
        this.user = user;
        this.value = value;
        this.validated = validated;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @return the validated
     */
    public Boolean getValidated() {
        return validated;
    }

    /**
     * @param validated the validated to set
     */
    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    

}
