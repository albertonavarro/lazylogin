package com.navid.lazylogin;

import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author alberto
 */
public class CreateTokenIT extends BaseIT {
    
    @Test
    public void shouldCreateTokenAndValidateIt() throws Exception {
        
        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail(testEmail);
        
        CreateTokenResponse ctresp = userCommands.createToken(ctreq);
    
        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
        
        String url = getUrlFromEmail();
        verifyUrl(url);
        
        GetInfoRequest requestInfo = new GetInfoRequest();
        requestInfo.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse responseInfo = userCommands.getInfo(requestInfo);
        
        Assert.notNull(responseInfo);
        Assert.isTrue(responseInfo.getStatus() == Status.VERIFIED);
    }
    
}
