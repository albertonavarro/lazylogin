package com.navid.lazylogin.persistence.jpa.domain;

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
@Table(name = "TOKEN")
public class TokenHb implements Serializable {

    @ManyToOne
    private UserHb user;

    @Id
    @Column(name = "TOKEN_VALUE")
    @GenericGenerator(name="token_gen", strategy="com.navid.lazylogin.persistence.jpa.TokenGenerator")
    @GeneratedValue(generator = "token_gen")
    private String value = null;

    @Column(name = "VALIDATED")
    private Boolean validated = false;

    /**
     * @return the user
     */
    public UserHb getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserHb user) {
        this.user = user;
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

    

    

}
