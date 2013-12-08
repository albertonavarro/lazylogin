/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.login.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alberto
 */
@Entity
@Table(name = "TOKEN")
public class Token implements Serializable {

    @ManyToOne
    private final User user;

    @Id
    @Column(name = "TOKEN_VALUE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long value = null;

    private Boolean verified = false;

    /**
     *
     * @param user
     */
    public Token(User user) {
        this.user = user;
    }

    /**
     *
     */
    protected Token() {
        this.user = null;
    }

    /**
     *
     * @return
     */
    public Long getValue() {
        return this.value;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
