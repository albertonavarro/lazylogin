package com.navid.lazylogin.springboot.cxf;

import com.lazylogin.client.user.v0.CreateTokenRequest;
import com.lazylogin.client.user.v0.CreateTokenResponse;
import com.lazylogin.client.user.v0.GetInfoRequest;
import com.lazylogin.client.user.v0.GetInfoResponse;
import com.lazylogin.client.user.v0.LoginWithTokenRequest;
import com.lazylogin.client.user.v0.LoginWithTokenResponse;
import com.lazylogin.client.user.v0.Sessionid;
import com.lazylogin.client.user.v0.Status;
import com.lazylogin.client.user.v0.Token;
import com.lazylogin.client.user.v0.UserCommands;
import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.services.SystemServices;
import com.navid.lazylogin.services.UserServicesImpl;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.NotFoundException;
import org.jdto.DTOBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController("userCommandsImpl")
@WebService(endpointInterface = "com.lazylogin.client.user.v0.UserCommands")
public class UserCommandsImpl implements UserCommands {

    private static final Logger LOG = LoggerFactory.getLogger(UserCommandsImpl.class.getName());

    @Resource
    private UserServicesImpl userServices;

    @Resource
    private SystemServices systemServices;

    @Resource(name = "lazylogin.endpoint.converter")
    private DTOBinder binder;


    /* (non-Javadoc)
     * @see com.lazylogin.client.user.v0.UserCommands#createToken(com.navid.lazylogin.CreateTokenRequest  parameters )*
     */
    @Override
    public CreateTokenResponse createToken(CreateTokenRequest parameters) {
        LOG.info("Executing operation createToken for email {}", parameters.getEmail());
        try {
            CreateTokenResponse _return = new CreateTokenResponse();

            SessionId ssoidCreated = userServices.createToken(parameters.getEmail());

            _return.setToken(binder.bindFromBusinessObject(Token.class, ssoidCreated.getToken()));
            _return.setSessionid(binder.bindFromBusinessObject(Sessionid.class, ssoidCreated));

            return _return;
        } catch (java.lang.Exception ex) {
            LOG.error("Error creating token for {}: {}", parameters.getEmail(), ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.lazylogin.client.user.v0.UserCommands#loginWithToken(com.navid.lazylogin.LoginWithTokenRequest  parameters )*
     */
    @Override
    public LoginWithTokenResponse loginWithToken(LoginWithTokenRequest parameters) {
        LOG.info("Executing operation loginWithToken with parameters", parameters);
        try {
            LoginWithTokenResponse _return = new LoginWithTokenResponse();

            SessionId ssoId = userServices.loginWithToken(parameters.getToken().getToken());

            _return.setResponse(binder.bindFromBusinessObject(Sessionid.class, ssoId));

            return _return;
        } catch (java.lang.Exception ex) {
            LOG.error("Error in loginWithToken with parameters {}: {}", parameters, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public GetInfoResponse getInfo(GetInfoRequest parameters) {
        LOG.info("Executing operation getInfo with parameters {}", parameters);

        SessionId ssoId = systemServices.getUserInfo(parameters.getSessionid());

        if (ssoId == null) {
            LOG.info("SessionId {} not found", parameters.getSessionid());
            throw new NotFoundException(parameters.getSessionid());
        }

        GetInfoResponse _return = new GetInfoResponse();
        if (ssoId.getToken().getValidated()) {
            _return.setStatus(Status.VERIFIED);
            _return.setName(ssoId.getToken().getUser().getName());
        } else {
            _return.setStatus(Status.UNVERIFIED);
        }

        return _return;
    }

}
