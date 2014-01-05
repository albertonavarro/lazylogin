package com.navid.login.eventproducer;

import com.navid.login.domain.ValidationKey;


/**
 *
 * @author alberto
 */
public interface EventProducer {
    
    void validateToken(ValidationKey token);
    
}
