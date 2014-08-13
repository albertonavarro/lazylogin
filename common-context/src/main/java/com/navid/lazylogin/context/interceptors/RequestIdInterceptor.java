/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.context.RequestContextContainer;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestIdInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdInterceptor.class);

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestIdInterceptor() {
        super(Phase.USER_PROTOCOL);
    }

    @Override
    public void handleMessage(Message message) {
        if (requestContextContainer != null && requestContextContainer.get().getRequestId() != null) {
            ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS)).put("RID", singletonList(requestContextContainer.get().getRequestId()));
            LOGGER.info("Adding RID header with value {}", requestContextContainer.get().getRequestId());
        } else {
            LOGGER.info("RID header not added, requestContextContainer or requestId not found");
        }
    }

    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }
}
