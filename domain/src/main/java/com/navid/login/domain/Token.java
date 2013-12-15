package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class Token implements Serializable {

    private final User user;

    private final Long value = null;

    private Boolean verified = false;

    /**
     *
     * @param user
     */
    public Token(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Long getValue() {
        return this.value;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
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
