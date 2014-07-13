package com.navid.lazylogin.persistence.hibernate.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name="VALIDATION_KEY")
    @GenericGenerator(name="validationkey_gen", strategy="com.navid.login.persistence.hibernate.ValidationKeyGenerator")
    @GeneratedValue(generator = "validationkey_gen")
    private String validationKey;

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
     * @return the validationKey
     */
    public String getValidationKey() {
        return validationKey;
    }

    /**
     * @param validationKey the validationKey to set
     */
    public void setValidationKey(String validationKey) {
        this.validationKey = validationKey;
    }

        
    
}
