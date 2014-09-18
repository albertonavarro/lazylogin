/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.lazylogin;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.client.ClientProperties;
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

        Assert.notNull(ctresp.getSessionid());
        Assert.notNull(ctresp.getToken());

        GetInfoRequest gireq = new GetInfoRequest();
        gireq.setSessionid(ctresp.getSessionid().getSessionid());
        GetInfoResponse giresp = userCommands.getInfo(gireq);

        Assert.notNull(giresp);
        Assert.isTrue(giresp.getStatus() == Status.UNVERIFIED);
        
        Assert.isTrue(greenMail.waitForIncomingEmail(1000, 1));
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
        Assert.isTrue(greenMail.waitForIncomingEmail(1000, 1));

        String url = extractUrlFromEmail(greenMail.getReceivedMessages()[emailPreviousIndex]);

        verifyUrl(url, Response.Status.FOUND.getStatusCode());
          
        //verifyUrl(url + "&username=user", Response.Status.OK.getStatusCode());
        
        //GetInfoResponse giresp2 = userCommands.getInfo(gireq);

        //Assert.notNull(giresp2);
        //Assert.isTrue(giresp2.getStatus() == Status.VERIFIED);
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
        
        Assert.isTrue(greenMail.waitForIncomingEmail(1000, 1));
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
