package com.navid.lazylogin;

import com.lazylogin.client.user.v0.CreateTokenRequest;
import com.lazylogin.client.user.v0.CreateTokenResponse;
import com.lazylogin.client.user.v0.GetInfoRequest;
import com.lazylogin.client.user.v0.GetInfoResponse;
import com.lazylogin.client.user.v0.LoginWithTokenRequest;
import com.lazylogin.client.user.v0.LoginWithTokenResponse;
import com.lazylogin.client.user.v0.Status;
import java.io.IOException;
import java.net.URL;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.hamcrest.MatcherAssert;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author alberto
 */
public class CreateTokenIT extends BaseIT {

    @Test
    public void shouldCreateUnverifiedToken() throws Exception {

        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("shouldCreateUnverifiedToken@someDomain");

        CreateTokenResponse ctresp = userCommands.createToken(ctreq);

        Assert.notNull(ctresp.getSessionid().getSessionid());
        Assert.notNull(ctresp.getToken().getToken());

        GetInfoRequest gireq = new GetInfoRequest();
        gireq.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse giresp = userCommands.getInfo(gireq);

        Assert.notNull(giresp);
        Assert.isTrue(giresp.getStatus() == Status.UNVERIFIED);
        
        Assert.isTrue(greenMail.waitForIncomingEmail(5000, 1));
    }

    @Test
    public void shouldVerifyToken() throws Exception {

        int emailPreviousIndex = greenMail.getReceivedMessages().length;

        CreateTokenRequest ctreq = new CreateTokenRequest();
        ctreq.setEmail("shouldVerifyToken@someDomain");

        CreateTokenResponse ctresp = userCommands.createToken(ctreq);

        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());

        GetInfoRequest gireq = new GetInfoRequest();
        gireq.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse giresp = userCommands.getInfo(gireq);

        Assert.notNull(giresp);
        Assert.isTrue(giresp.getStatus() == Status.UNVERIFIED);
        Assert.isNull(giresp.getName());

        Assert.isTrue(greenMail.waitForIncomingEmail(5000, 1));

        String url = extractUrlFromEmail(greenMail.getReceivedMessages()[emailPreviousIndex]);
        
        System.out.println("URL Detected: " + url);

        verifyUrl(url, Response.Status.FOUND.getStatusCode());
        
        URL urlParsed = new URL(url);
        URL newURL = new URL(
                urlParsed.getProtocol(), 
                urlParsed.getHost(), 
                urlParsed.getPort(), 
                "/verifyWithUsername?"+ urlParsed.getQuery()+"&username=user");
        
        verifyUrl(newURL.toString(), Response.Status.OK.getStatusCode());
        
        GetInfoResponse giresp2 = userCommands.getInfo(gireq);

        Assert.notNull(giresp2);
        Assert.isTrue(giresp2.getStatus() == Status.VERIFIED);
        Assert.isTrue(giresp2.getName().equals("user"));
    }
    
    @Test
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
        
        Assert.isTrue(greenMail.waitForIncomingEmail(5000, 1));
    }

    private String extractUrlFromEmail(MimeMessage content) throws IOException, MessagingException {
        String urlContent = content.getContent().toString();
        String url = urlContent.substring(0, urlContent.length() -2);
        return url;
    }
    
    private void verifyUrl(String url, int code) {
        Client client = ClientBuilder.newBuilder().build();
        Response response = client.target(url).property(ClientProperties.FOLLOW_REDIRECTS, false).request().get();
        Assert.isTrue(response.getStatus() == code);
    }

}
