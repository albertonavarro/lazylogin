package com.navid.login.persistence.hibernate;

import com.navid.login.domain.DomainFactory;
import com.navid.login.persistence.hibernate.domain.SsoIdHb;
import com.navid.login.persistence.hibernate.domain.ValidationKeyHb;
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
