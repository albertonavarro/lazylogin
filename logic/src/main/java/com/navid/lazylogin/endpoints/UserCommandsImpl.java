package com.navid.lazylogin.endpoints;

import com.navid.lazylogin.CreateTokenRequest;
import com.navid.lazylogin.CreateTokenResponse;
import com.navid.lazylogin.LoginWithTokenRequest;
import com.navid.lazylogin.LoginWithTokenResponse;
import com.navid.lazylogin.Sessionid;
import com.navid.lazylogin.Token;
import com.navid.lazylogin.UserCommands;
import com.navid.lazylogin.domain.SsoId;
import com.navid.lazylogin.services.UserServices;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.jdto.DTOBinder;


@javax.jws.WebService(endpointInterface = "com.navid.lazylogin.UserCommands")
public class UserCommandsImpl implements UserCommands {

    private static final Logger LOG = Logger.getLogger(UserCommandsImpl.class.getName());
    
    @Resource
    private UserServices userServices;
    
    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;
    

    /* (non-Javadoc)
     * @see com.navid.lazylogin.UserCommands#createToken(com.navid.lazylogin.CreateTokenRequest  parameters )*
     */
    @Override
    public com.navid.lazylogin.CreateTokenResponse createToken(CreateTokenRequest parameters) { 
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
     * @see com.navid.lazylogin.UserCommands#loginWithToken(com.navid.lazylogin.LoginWithTokenRequest  parameters )*
     */
    @Override
    public com.navid.lazylogin.LoginWithTokenResponse loginWithToken(LoginWithTokenRequest parameters) { 
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
