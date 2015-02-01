package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class RequestContextCreatorInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextCreatorInterceptor.class);

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestContextCreatorInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {
        List<String> corrList = ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS)).get(HeaderConstants.CORRELATION_ID);

        RequestContext requestContext = requestContextContainer.get();

        if (corrList != null && corrList.size() > 0) {
            LOGGER.info("Message intercepted with Correlation Id: {}", corrList.get(0));
            requestContext.setCorrelationId(corrList.get(0));
        } else {
            String newUUID = UUID.randomUUID().toString();
            LOGGER.info("Message intercepted without Correlation Id, new one generated with value {}", newUUID);
            requestContext.setCorrelationId(newUUID);
        }

        List<String> sessionList = ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS)).get(HeaderConstants.SESSION_ID);

        if (sessionList != null && sessionList.size() > 0) {
            LOGGER.info("Message intercepted with Session Id: {}", sessionList.get(0));
            requestContext.setSessionId(sessionList.get(0));
        } else {
            LOGGER.info("Message intercepted without SessionId");
        }
    }

    @Override
    public void handleFault(Message messageParam) {
        requestContextContainer.get();
    }

    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }
}
