/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This OUT handler is going to fill RID and SID in RS client
 */
public final class RequestIdHandler implements Handler<MessageContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdHandler.class);

    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public boolean handleMessage(MessageContext message) {

        RequestContext requestContext = requestContextContainer.get();
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);

        if (requestContext.getCorrelationId() != null) {
            headers.put(HeaderConstants.CORRELATION_ID, Arrays.asList(new String[]{requestContext.getCorrelationId()}));
            LOGGER.info("Adding RID header with value {}", requestContext.getCorrelationId());
        }

        if (requestContext.getSessionId() != null) {
            headers.put(HeaderConstants.SESSION_ID, Arrays.asList(new String[]{requestContext.getSessionId()}));
            LOGGER.info("Adding SID header with value {}", requestContext.getSessionId());
        }

        return true;
    }

    @Override
    public boolean handleFault(MessageContext c) {
        return false;
    }

    @Override
    public void close(MessageContext mc) {

    }

    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

}
