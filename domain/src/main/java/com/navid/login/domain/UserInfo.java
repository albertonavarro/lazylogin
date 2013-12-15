package com.navid.login.domain;

/**
 *
 * @author alberto
 */
public class UserInfo {

    private final UserId userId;

    private final String email;

    private Boolean validated;

    public UserInfo( UserId userId, String email ) {
        this.userId = userId;
        this.email = email;
    }

    public UserId getUserId(){
        return userId;
    }

    public String getEmail(){
        return email;
    }

    public void setValidated( Boolean validated ) {
        this.validated = validated;
    }

    public Boolean isValidated() {
        return validated;
    }
    
}
