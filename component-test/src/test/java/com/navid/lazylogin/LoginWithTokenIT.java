/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.lazylogin;

import org.springframework.util.Assert;

/**
 *
 * @author alberto
 */
public class LoginWithTokenIT extends BaseIT {
    
    public void loginWithUnverifiedToken() throws Exception {
        
        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("loginWithUnverifiedToken@someDomain");

        CreateTokenResponse ctresp = userCommands.createToken(ctreq);

        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
    
        LoginWithTokenRequest loginReq = new LoginWithTokenRequest();
        loginReq.setToken(ctresp.getToken());
        
        LoginWithTokenResponse loginResp = userCommands.loginWithToken(loginReq);
        Assert.notNull(loginResp);
        Assert.notNull(loginResp.getResponse());
        
        Assert.isTrue(greenMail.waitForIncomingEmail(1000, 1));
    }
    
}
