package com.navid.lazylogin.eventproducer;

import com.navid.lazylogin.domain.ValidationKey;

/**
 *
 * @author alberto
 */
public interface EventProducer {

    void validateToken(ValidationKey token);

}
