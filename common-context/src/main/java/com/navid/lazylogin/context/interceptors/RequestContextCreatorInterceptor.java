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

public class RequestContextCreatorInterceptor extends AbstractPhaseInterceptor<Message> {

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestContextCreatorInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {
        RequestContext requestContext = requestContextContainer.create();
        if(((Map<String, List<String>>)message.getContextualProperty(Message.PROTOCOL_HEADERS)).get("RID") != null) {
            requestContext.setRequestId(((Map<String, List<String>>)message.getContextualProperty(Message.PROTOCOL_HEADERS)).get("RID").get(0));
        } else {
            requestContext.setRequestId(UUID.randomUUID().toString());
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
