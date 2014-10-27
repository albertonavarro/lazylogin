/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This OUT interceptor is used to fill the headers with SID and RID
 */
public class RequestIdInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdInterceptor.class);

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestIdInterceptor() {
        super(Phase.POST_LOGICAL);
    }

    @Override
    public void handleMessage(Message message) {
        if (requestContextContainer != null) {
            RequestContext requestContext = requestContextContainer.get();

            if (requestContext.getSessionId() != null) {
                ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS))
                        .put(HeaderConstants.SESSION_ID, singletonList(requestContext.getSessionId()));
                LOGGER.info("Adding SID header with value {}", requestContext.getSessionId());
            } else {
                LOGGER.info("SID header not added, sessionId not found");
            }

            //if (requestContextContainer.get().getCorrelationId() == null) {
            String newCorrelationId = UUID.randomUUID().toString();
            LOGGER.info("Generating a new correlationId with value {}", newCorrelationId);
            requestContext.setCorrelationId(newCorrelationId);
            //}

            ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS))
                    .put(HeaderConstants.CORRELATION_ID, singletonList(requestContext.getCorrelationId()));
            LOGGER.info("Adding RID header with value {}", requestContext.getCorrelationId());
        }
    }

    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }
}
