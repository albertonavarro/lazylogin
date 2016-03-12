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

        CreateTokenRequest createTokenRequest = new CreateTokenRequest();
        createTokenRequest.setEmail(testEmail);
        CreateTokenResponse ctresp = userCommands.createToken(createTokenRequest);

        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());

        String url = getUrlFromEmail();
        verifyUrl(url);

        GetInfoRequest getInfoRequest = new GetInfoRequest();
        getInfoRequest.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse responseInfo = userCommands.getInfo(getInfoRequest);

        Assert.notNull(responseInfo);
        Assert.isTrue(responseInfo.getStatus() == Status.VERIFIED);
    }

}
