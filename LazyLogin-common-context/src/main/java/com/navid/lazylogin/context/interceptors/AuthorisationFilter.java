/*
 */
package com.navid.lazylogin.context.interceptors;

import com.navid.login.SystemCommands;
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

        final com.navid.login.UserInfo userInfo = systemCommands.getUserInfo(requestContextContainer.get().getRequestId());
        requestContextContainer.get().setUserId(userInfo.getUsername());

    }

}
