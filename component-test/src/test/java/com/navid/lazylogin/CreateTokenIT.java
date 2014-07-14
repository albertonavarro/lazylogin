/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.lazylogin;

import org.testng.annotations.Test;

/**
 *
 * @author alberto
 */
public class CreateTokenIT extends BaseIT {
    
    @Test
    public void smokeTest() {
        
        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("someEmail@someDomain");
        
        CreateTokenResponse ctresp = userCommands.createToken(ctreq);
    
        
    }
    
}
