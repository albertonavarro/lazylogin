/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.lazylogin;

import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author alberto
 */
public class CreateTokenIT extends BaseIT {
    
    @Test
    public void shouldCreateUnverifiedToken() {
        
        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("shouldCreateUnverifiedToken@someDomain");
        
        CreateTokenResponse ctresp = userCommands.createToken(ctreq);
    
        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
        
        GetInfoRequest gireq = new GetInfoRequest();
        gireq.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse giresp = userCommands.getInfo(gireq);
        
        Assert.notNull(giresp);
        Assert.isTrue(giresp.getStatus() == Status.UNVERIFIED);
    }
    
    @Test
    public void shouldCreateVerifiedToken() {
        
        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("shouldCreateVerifiedToken@someDomain");
        
        CreateTokenResponse ctresp = userCommands.createToken(ctreq);
    
        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
        
        GetInfoRequest gireq = new GetInfoRequest();
        gireq.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse giresp = userCommands.getInfo(gireq);
        
        Assert.notNull(giresp);
        Assert.isTrue(giresp.getStatus() == Status.VERIFIED);
    }
    
}
