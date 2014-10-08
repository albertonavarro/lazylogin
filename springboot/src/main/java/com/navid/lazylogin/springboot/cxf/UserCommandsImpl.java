package com.navid.lazylogin.springboot.cxf;

import com.navid.lazylogin.CreateTokenRequest;
import com.navid.lazylogin.CreateTokenResponse;
import com.navid.lazylogin.GetInfoRequest;
import com.navid.lazylogin.GetInfoResponse;
import com.navid.lazylogin.LoginWithTokenRequest;
import com.navid.lazylogin.LoginWithTokenResponse;
import com.navid.lazylogin.Sessionid;
import com.navid.lazylogin.Status;
import com.navid.lazylogin.Token;
import com.navid.lazylogin.UserCommands;
import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.services.SystemServices;
import com.navid.lazylogin.services.UserServices;
import javax.annotation.Resource;
import javax.ws.rs.NotFoundException;
import org.jdto.DTOBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@javax.jws.WebService(endpointInterface = "com.navid.lazylogin.UserCommands")
public class UserCommandsImpl implements UserCommands {

    private static final Logger LOG = LoggerFactory.getLogger(UserCommandsImpl.class.getName());

    @Resource
    private UserServices userServices;

    @Resource
    private SystemServices systemServices;

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

            SessionId ssoidCreated = userServices.createToken(parameters.getEmail());

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
        LOG.info("Executing operation loginWithToken with parameters");
        try {
            LoginWithTokenResponse _return = new LoginWithTokenResponse();

            SessionId ssoId = userServices.loginWithToken(parameters.getToken().getToken());

            _return.setResponse(binder.bindFromBusinessObject(Sessionid.class, ssoId));

            return _return;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public GetInfoResponse getInfo(GetInfoRequest parameters) {
        LOG.info("Executing operation getInfo with sessionId {}", parameters.getSessionid());

        SessionId ssoId = systemServices.getUserInfo(parameters.getSessionid());

        if (ssoId == null) {
            LOG.info("SessionId {} not found", parameters.getSessionid());
            throw new NotFoundException(parameters.getSessionid());
        }

        GetInfoResponse _return = new GetInfoResponse();
        if( ssoId.getToken().getValidated()){
            _return.setStatus(Status.VERIFIED);
            _return.setName(ssoId.getToken().getUser().getName());
        } else {
            _return.setStatus(Status.UNVERIFIED);
        }
        
        return _return;
    }

}
