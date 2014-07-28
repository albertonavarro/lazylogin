package com.navid.lazylogin.persistence.hibernate.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alberto
 */
@Entity
@Table(name = "SSOID")
public class SessionIdHb implements Serializable {

    @ManyToOne
    private TokenHb token;

    @Id
    @Column(name = "SSOID_VALUE")
    @GenericGenerator(name="sessionid_gen", strategy="com.navid.lazylogin.persistence.hibernate.SessionIdGenerator")
    @GeneratedValue(generator = "sessionid_gen")
    private String value = null;

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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    

}
