/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.persistence.hibernate;

import com.navid.login.domain.DomainFactory;
import com.navid.login.persistence.hibernate.domain.SsoIdHb;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 *
 * @author alberto
 */
public class SessionIdGenerator implements IdentifierGenerator {

    public Serializable generate(SessionImplementor si, Object o) throws HibernateException {
        SsoIdHb sessionId = (SsoIdHb) o;
        
        return DomainFactory.sessionIdGenerator(sessionId.getToken().getUser().getEmail(), sessionId.getToken().getValue());
    }
    
}
