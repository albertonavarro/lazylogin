/*
 */
package com.navid.lazylogin.context.interceptors;

import com.lazylogin.client.system.v0.GetUserInfoError_Exception;
import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.context.RequestContextContainer;
import java.io.IOException;
import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Built in class for calling Lazylogin system endpoint and retrieve user info
 */
public class AuthorisationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisationFilter.class);

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String sessionId = requestContextContainer.get().getSessionId();

        LOGGER.info("Authorisating request for sessionId: {}", sessionId);

        if (requestContextContainer.get().getSessionId() != null) {
            try {
                final UserInfo userInfo = systemCommands.getUserInfo(sessionId);
                LOGGER.debug("UserInfo discovered: {}", userInfo);
                requestContextContainer.get().setUserId(userInfo.getUserid());
                requestContextContainer.get().setUserName(userInfo.getUsername());
            } catch (GetUserInfoError_Exception e) {
                LOGGER.error("Error authenticating with sessionId" + sessionId, e);
                throw new AuthenticationException("Error authenticating client with sessionId " + sessionId);
            }
        } else {
            throw new AuthenticationException("SessionId can't be null.");
        }

    }

    public void setSystemCommands(SystemCommands systemCommands) {
        this.systemCommands = systemCommands;
    }

    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

}
