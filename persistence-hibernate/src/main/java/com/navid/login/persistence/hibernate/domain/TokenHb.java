package com.navid.login.persistence.hibernate.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
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
public class TokenHb implements Serializable {

    @ManyToOne
    private UserHb user;

    @Id
    @Column(name = "TOKEN_VALUE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long value = null;

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
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
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
