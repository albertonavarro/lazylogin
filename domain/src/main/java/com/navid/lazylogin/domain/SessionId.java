package com.navid.lazylogin.domain;

import com.google.common.base.Objects;
import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class SessionId implements Serializable {

    private final Token token;

    private final String value;

    public SessionId(Token parentToken, String value) {
        this.token = parentToken;
        this.value = value;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("token", token).add("value", value).toString();
    }

}
