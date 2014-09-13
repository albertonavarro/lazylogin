package com.navid.lazylogin.domain;

import com.google.common.base.Objects;
import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class User implements Serializable {

    private final String email;

    private String name;

    private final UserId userId;

    public User(String email, UserId userId) {
        this.email = email;
        this.userId = userId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the userId
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("email", email).add("userId", userId).toString();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    
}
