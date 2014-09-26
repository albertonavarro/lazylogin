package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.domain.DomainFactory;
import com.navid.lazylogin.persistence.jpa.domain.SessionIdHb;
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
        SessionIdHb sessionId = (SessionIdHb) o;
        
        return DomainFactory.sessionIdGenerator(sessionId.getToken().getUser().getEmail(), sessionId.getToken().getValue());
    }
    
}
