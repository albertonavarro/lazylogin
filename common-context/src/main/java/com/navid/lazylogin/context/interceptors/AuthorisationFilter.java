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

public class AuthorisationFilter implements ContainerRequestFilter {

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final UserInfo userInfo = systemCommands.getUserInfo(requestContextContainer.get().getRequestId());
        requestContextContainer.get().setUserId(userInfo.getUsername());
    }
    
    public void setSystemCommands( SystemCommands systemCommands ) {
        this.systemCommands = systemCommands;
    }
    
    public void setRequestContextContainer( RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

}
