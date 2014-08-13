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

public class RequestContextCreatorInterceptor extends AbstractPhaseInterceptor<Message> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextCreatorInterceptor.class);

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestContextCreatorInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {
        List<String> ridList = ((Map<String, List<String>>)message.getContextualProperty(Message.PROTOCOL_HEADERS)).get("RID");
        
        RequestContext requestContext = requestContextContainer.create();
        
        if( ridList != null && ridList.size() > 0) {
            LOGGER.info("Message intercepted with RID: {}", ridList.get(0));
            requestContext.setRequestId(ridList.get(0));
        } else {
            String newUUID = UUID.randomUUID().toString();
            LOGGER.info("Message intercepted without RID, new one generated with value {}", newUUID);
            requestContext.setRequestId(newUUID);
        }
    }

    @Override
    public void handleFault(Message messageParam) {
        requestContextContainer.create();
    }
    
    public void setRequestContextContainer( RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }
}
