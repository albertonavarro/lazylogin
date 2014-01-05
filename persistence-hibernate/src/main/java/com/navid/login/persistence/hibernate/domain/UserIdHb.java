/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
