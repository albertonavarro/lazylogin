package com.navid.login.persistence.hibernate.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author alberto
 */
@Entity
@Table(name="VALIDATION_KEYS")
public class ValidationKeyHb implements Serializable {
    
    @OneToOne
    private TokenHb token;
    
    @Id
    @Column(name="TOKEN_VALUE")
    private String verificationCode;

    /**
     * @return the token
     */
    public TokenHb getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(TokenHb token) {
        this.token = token;
    }

    /**
     * @return the verificationCode
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * @param verificationCode the verificationCode to set
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    
    
}
