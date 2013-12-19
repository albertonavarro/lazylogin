package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class Token implements Serializable {

    private final User user;

    private Long value;

    private Boolean verified = false;
    
    /**
     * 
     * @param user
     * @param value 
     */
    public Token(User user, Long value) {
        this.user = user;
        this.value = value;
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
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * @return the verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
