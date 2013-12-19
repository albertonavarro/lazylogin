package com.navid.login.endpoints;

import com.navid.login.CreateTokenRequest;
import com.navid.login.CreateTokenResponse;
import com.navid.login.LoginWithTokenRequest;
import com.navid.login.LoginWithTokenResponse;
import com.navid.login.Sessionid;
import com.navid.login.Token;
import com.navid.login.UserCommands;
import com.navid.login.domain.SsoId;
import com.navid.login.services.UserServices;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.jdto.DTOBinder;


@javax.jws.WebService(endpointInterface = "com.navid.login.UserCommands")
public class UserCommandsImpl implements UserCommands {

    private static final Logger LOG = Logger.getLogger(UserCommandsImpl.class.getName());
    
    @Resource
    private UserServices userServices;
    
    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;
    

    /* (non-Javadoc)
     * @see com.navid.login.UserCommands#createToken(com.navid.login.CreateTokenRequest  parameters )*
     */
    @Override
    public com.navid.login.CreateTokenResponse createToken(CreateTokenRequest parameters) { 
        LOG.info("Executing operation createToken");
        try {
            CreateTokenResponse _return = new CreateTokenResponse();
            
            SsoId ssoidCreated = userServices.createToken(parameters.getEmail());
            
            _return.setToken(binder.bindFromBusinessObject(Token.class, ssoidCreated.getToken()));
            _return.setSessionid(binder.bindFromBusinessObject(Sessionid.class, ssoidCreated));
            
            return _return;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.navid.login.UserCommands#loginWithToken(com.navid.login.LoginWithTokenRequest  parameters )*
     */
    @Override
    public com.navid.login.LoginWithTokenResponse loginWithToken(LoginWithTokenRequest parameters) { 
        LOG.info("Executing operation loginWithToken");
        try {
            LoginWithTokenResponse _return = new LoginWithTokenResponse();
            
            SsoId ssoId = userServices.loginWithToken(parameters.getToken().getToken());

            _return.setResponse(binder.bindFromBusinessObject(Sessionid.class, ssoId));
            
            return _return;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
