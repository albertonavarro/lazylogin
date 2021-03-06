package com.navid.lazylogin.domain;

import com.google.common.base.Objects;
import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class Token implements Serializable {

    private final User user;

    private final String value;

    private Boolean validated;

    /**
     *
     * @param user
     * @param value
     * @param validated
     */
    public Token(User user, String value, Boolean validated) {
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
    public String getValue() {
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("value", value).add("user", user).toString();
    }

}
