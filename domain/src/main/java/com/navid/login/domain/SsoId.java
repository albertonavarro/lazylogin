package com.navid.login.domain;

import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class SsoId implements Serializable {

    private final Token token;

    private final String value = null;

    public SsoId(Token parentToken) {
        this.token = parentToken;
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

}
