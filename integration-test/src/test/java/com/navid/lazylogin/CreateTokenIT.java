package com.navid.lazylogin;

import org.springframework.util.Assert;
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
    
        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
    }
    
}
