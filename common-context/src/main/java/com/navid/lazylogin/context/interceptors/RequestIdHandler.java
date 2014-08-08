/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.context.RequestContextContainer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.message.Message;

public class RequestIdHandler implements Handler<MessageContext> {
    
    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public boolean handleMessage(MessageContext message) {
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        headers.put("RID", Arrays.asList(new String[]{requestContextContainer.get().getRequestId()}));

        return true;
    }

    @Override
    public boolean handleFault(MessageContext c) {
        return false;
    }

    @Override
    public void close(MessageContext mc) {

    }
    
    public void setRequestContextContainer( RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

}
