package com.navid.lazylogin.springboot.cxf;

import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.services.SystemServices;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;

import org.jdto.DTOBinder;
import org.springframework.stereotype.Component;

@Component("systemCommandsImpl")
@WebService(endpointInterface = "com.lazylogin.client.system.v0.SystemCommands")
public class SystemCommandsImpl implements SystemCommands {

    private static final Logger LOG = Logger.getLogger(SystemCommandsImpl.class.getName());

    @Resource
    private SystemServices systemServices;

    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;

    @Override
    public UserInfo getUserInfo(String sessionId) {
        SessionId ssoId = systemServices.getUserInfo(sessionId);

        UserInfo userInfo = new UserInfo();
        userInfo.setVerified(ssoId.getToken().getValidated());

        if (ssoId.getToken().getValidated()) {
            userInfo.setUserid(ssoId.getToken().getUser().getUserId().getValue());
            userInfo.setUsername(ssoId.getToken().getUser().getName());
        }

        return userInfo;
    }

}
