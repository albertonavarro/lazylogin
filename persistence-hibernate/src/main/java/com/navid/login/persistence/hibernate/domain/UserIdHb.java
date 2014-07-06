package com.navid.login.persistence.hibernate.domain;

/**
 *
 * @author alberto
 */
public class UserIdHb {
    
    private final Long userId;
    
    public UserIdHb( Long userId ) {
        this.userId = userId;
    }
    

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }
    
}
