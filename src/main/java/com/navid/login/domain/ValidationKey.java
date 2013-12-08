/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.domain;

import java.io.Serializable;
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
public class ValidationKey implements Serializable {
    
    @OneToOne
    private final Token token;
    
    @Id
    @Column(name="TOKEN_VALUE")
    private final String verificationCode;
    
    protected ValidationKey() {
        this.token = null;
        this.verificationCode = null;
    }
    
    public ValidationKey(Token token, String verificationCode ) {
        this.token = token;
        this.verificationCode = verificationCode;
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
    public String getVerificationCode() {
        return verificationCode;
    }
    
    
}
