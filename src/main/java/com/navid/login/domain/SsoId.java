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
@Table(name = "SSOID")
public class SsoId implements Serializable {

    @ManyToOne
    private final Token token;

    @Id
    @Column(name = "SSOID_VALUE")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private final String value = null;

    protected SsoId() {
        this.token = null;
    }

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
