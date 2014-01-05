package com.navid.login.eventproducer;

import com.navid.login.domain.Token;


/**
 *
 * @author alberto
 */
public interface EventProducer {
    
    void validateToken(Token token);
    
}
