/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.lazylogin.SystemCommands;
import com.navid.lazylogin.UserInfo;
import com.navid.lazylogin.context.RequestContextContainer;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorisationFilter implements ContainerRequestFilter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisationFilter.class);

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Authorisating request for requestId: {}", requestContextContainer.get().getRequestId());
        
        final UserInfo userInfo = systemCommands.getUserInfo(requestContextContainer.get().getRequestId());
        
        LOGGER.debug("UserInfo discovered: {}", userInfo);
        requestContextContainer.get().setUserId(userInfo.getUsername());
    }
    
    public void setSystemCommands( SystemCommands systemCommands ) {
        this.systemCommands = systemCommands;
    }
    
    public void setRequestContextContainer( RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

}
