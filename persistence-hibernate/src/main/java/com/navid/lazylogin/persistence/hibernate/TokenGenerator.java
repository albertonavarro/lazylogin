package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.domain.DomainFactory;
import com.navid.lazylogin.persistence.hibernate.domain.SessionIdHb;
import com.navid.lazylogin.persistence.hibernate.domain.TokenHb;
import java.io.Serializable;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 *
 * @author alberto
 */
public class TokenGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor si, Object o) throws HibernateException {
        TokenHb token = (TokenHb) o;
        
        return DomainFactory.tokenGenerator(token.getUser().getEmail());
    } 
    
}
