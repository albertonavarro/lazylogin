/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.lazylogin;

import com.lazylogin.client.user.v0.*;
import javax.xml.ws.soap.SOAPFaultException;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;


/**
 *
 * @author alberto
 */
public class LoginWithTokenIT extends BaseIT {

    @Test
    public void shouldLoginWithUnverifiedToken() throws Exception {

        //Given unverified token
        CreateTokenRequest ctreq = new CreateTokenRequest().withEmail("loginWithUnverifiedToken@someDomain");
        Token token = userCommands.createToken(ctreq).getToken();

        //When we login with an unverified token
        LoginWithTokenRequest loginReq = new LoginWithTokenRequest().withToken(token);
        LoginWithTokenResponse loginResp = userCommands.loginWithToken(loginReq);

        //then
        assertNotNull(loginResp.getResponse().getSessionid());
    }
    
    @Test(expectedExceptions = SOAPFaultException.class)
    public void shouldNotAllowUnexistingSessionIds() throws Exception {

        //Given unexisting token
        String token = "1-0000000000-aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa-000000000000";
        
        //When we login with an unverified token
        LoginWithTokenRequest loginReq = new LoginWithTokenRequest().withToken(new Token().withToken(token));
        LoginWithTokenResponse loginResp = userCommands.loginWithToken(loginReq);

        //then
        fail();
    }
}
