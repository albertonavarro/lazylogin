package com.navid.lazylogin.springboot.cxf;

import com.lazylogin.client.system.v0.GetUserInfoError_Exception;
import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.services.SystemServices;
import javax.annotation.Resource;
import javax.jws.WebService;

import org.jdto.DTOBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController("systemCommandsImpl")
@WebService(endpointInterface = "com.lazylogin.client.system.v0.SystemCommands")
public class SystemCommandsImpl implements SystemCommands {

    private static final Logger LOG = LoggerFactory.getLogger(SystemCommandsImpl.class.getName());

    @Resource
    private SystemServices systemServices;

    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;

    @Override
    public UserInfo getUserInfo(String sessionId) throws GetUserInfoError_Exception {
        LOG.info("Executing operation System.getUserInfo with session {}", sessionId);

        SessionId ssoId = systemServices.getUserInfo(sessionId);

        if(ssoId == null) {
            LOG.info("Session not found: {}", sessionId);
            throw new GetUserInfoError_Exception("Session " + sessionId + "not found");
        }

        LOG.info("Session found: {}", ssoId);
        UserInfo userInfo = new UserInfo();
        userInfo.setVerified(ssoId.getToken().getValidated());

        if (ssoId.getToken().getValidated()) {
            userInfo.setUserid(ssoId.getToken().getUser().getUserId().getValue());
            userInfo.setUsername(ssoId.getToken().getUser().getName());
        }

        return userInfo;
    }

}
