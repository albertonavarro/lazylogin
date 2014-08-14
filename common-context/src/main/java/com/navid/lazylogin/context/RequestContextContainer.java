/*
 */
package com.navid.lazylogin.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestContextContainer {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestContextContainer.class);

    private final ThreadLocal<RequestContext> requestContext = new ThreadLocal<>();

    public RequestContext get() {
        RequestContext context = requestContext.get();
        if (context == null) {
            return create();
        }
        return context;
    }

    public RequestContext delete() {
        RequestContext temp = requestContext.get();
        requestContext.set(null);
        LOGGER.debug("Request context deleted: {}", temp);
        return temp;
    }

    private RequestContext create() {
        RequestContext temp = new RequestContext();
        requestContext.set(temp);
        LOGGER.debug("Request context created: {}", temp);
        return temp;
    }

}
