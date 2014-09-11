package com.navid.lazylogin;

import com.lazylogin.client.user.v0.CreateTokenRequest;
import com.lazylogin.client.user.v0.CreateTokenResponse;
import com.lazylogin.client.user.v0.GetInfoRequest;
import com.lazylogin.client.user.v0.GetInfoResponse;
import com.lazylogin.client.user.v0.Status;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author alberto
 */
public class CreateTokenIT extends BaseIT {
    
    @Test
    public void shouldCreateTokenAndValidateIt() throws Exception {
        
        CreateTokenResponse ctresp = userCommands.createToken(new CreateTokenRequest().withEmail(testEmail));
    
        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());
        
        String url = getUrlFromEmail();
        verifyUrl(url);
        
        GetInfoResponse responseInfo = userCommands.getInfo(new GetInfoRequest().withSessionid(ctresp.getSessionid().getSessionid()));
        
        Assert.notNull(responseInfo);
        Assert.isTrue(responseInfo.getStatus() == Status.VERIFIED);
    }
    
}
