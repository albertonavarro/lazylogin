
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.navid.lazylogin.endpoints;

import com.navid.lazylogin.SystemCommands;
import com.navid.lazylogin.UserInfo;
import com.navid.lazylogin.services.SystemServices;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.jdto.DTOBinder;


@javax.jws.WebService(endpointInterface = "com.navid.login.SystemCommands")
                      
public class SystemCommandsImpl implements SystemCommands {

    private static final Logger LOG = Logger.getLogger(SystemCommandsImpl.class.getName());
    
    @Resource
    private SystemServices systemServices;
    
    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;

    @Override
    public UserInfo getUserInfo(String sessionId) { 
        UserInfo userInfo = new UserInfo();
        
        com.navid.lazylogin.domain.SsoId ssoId = systemServices.getUserInfo(sessionId);
        
        userInfo.setUserid(ssoId.getToken().getUser().getUserId().getValue());
        userInfo.setVerified(ssoId.getToken().getValidated());
        userInfo.setUsername(ssoId.getToken().getUser().getName());
        
        return userInfo;
    }

}
