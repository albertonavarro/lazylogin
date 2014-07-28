package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.domain.DomainFactory;
import com.navid.lazylogin.persistence.hibernate.domain.SessionIdHb;
import com.navid.lazylogin.persistence.hibernate.domain.ValidationKeyHb;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 *
 * @author alberto
 */
public class ValidationKeyGenerator  implements IdentifierGenerator {

    public Serializable generate(SessionImplementor si, Object o) throws HibernateException {
        ValidationKeyHb sessionId = (ValidationKeyHb) o;
        
        return DomainFactory.validationKeyGenerator(sessionId.getToken().getValue());
    }
}
