package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.domain.DomainFactory;
import com.navid.lazylogin.persistence.hibernate.domain.SsoIdHb;
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
