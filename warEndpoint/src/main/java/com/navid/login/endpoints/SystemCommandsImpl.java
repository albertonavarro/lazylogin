
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.navid.login.endpoints;

import com.navid.login.SystemCommands;
import com.navid.login.UserInfo;
import com.navid.login.services.SystemServices;
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
        
        com.navid.login.domain.UserInfo userInfoDomain = systemServices.getUserInfo(sessionId);
        
        userInfo.setEmail(userInfoDomain.getEmail());
        userInfo.setVerified(userInfoDomain.isValidated());
        userInfo.setUserId(userInfoDomain.getUserId().getValue());
        
        return userInfo;
    }

}
