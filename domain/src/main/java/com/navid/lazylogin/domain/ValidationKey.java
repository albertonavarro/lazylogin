package com.navid.lazylogin.domain;

import com.google.common.base.Objects;
import java.io.Serializable;

/**
 *
 * @author alberto
 */
public class ValidationKey implements Serializable {

    private final Token token;

    private final String validationKey;

    public ValidationKey(Token token, String validationKey) {
        this.token = token;
        this.validationKey = validationKey;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the verificationCode
     */
    public String getValidationKey() {
        return validationKey;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("token", token).add("validationKey", validationKey).toString();
    }

}
