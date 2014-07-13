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

public class RequestIdInterceptor extends AbstractPhaseInterceptor<Message> {

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestIdInterceptor() {
        super(Phase.SEND);
    }

    @Override
    public void handleMessage(Message message) {
        if (requestContextContainer != null && requestContextContainer.get().getRequestId() != null) {
                ((Map<String, List<String>>) message.getContextualProperty(Message.PROTOCOL_HEADERS)).put("RID", singletonList(requestContextContainer.get().getRequestId()));
            
        }

    }
}