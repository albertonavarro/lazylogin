package com.navid.lazylogin;

import com.lazylogin.client.user.v0.CreateTokenRequest;
import com.lazylogin.client.user.v0.CreateTokenResponse;
import com.lazylogin.client.user.v0.GetInfoRequest;
import com.lazylogin.client.user.v0.GetInfoResponse;
import com.lazylogin.client.user.v0.Status;
import java.io.IOException;
import java.net.URL;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 *
 * @author alberto
 */
public class CreateTokenCT extends BaseCT {

    @Test
    public void shouldCreateToken() throws Exception {
        //Given
        final String newEmail = "shouldCreateToken@CreateTokenIT";

        //when token is created
        CreateTokenResponse ctresp = userCommands.createToken(new CreateTokenRequest(){{setEmail(newEmail);}});

        //Then Session and tokens aren't coming null
        assertNotNull(ctresp.getSessionid().getSessionid());
        assertNotNull(ctresp.getToken().getToken());
    }
    
    @Test
    public void shouldCreateTokenWithExistingUser() throws Exception {
        //Given
        final String newEmail = "shouldCreateTokenWithExistingUser@CreateTokenIT";
        userCommands.createToken(new CreateTokenRequest() {{
            setEmail(newEmail);
        }});

        //when new token is created with the same user (Email)
        CreateTokenResponse ctresp = userCommands.createToken(new CreateTokenRequest(){{setEmail(newEmail);}});
        
        //Then Session and tokens aren't coming null
        assertNotNull(ctresp.getSessionid().getSessionid());
        assertNotNull(ctresp.getToken().getToken());
    }

    @Test
    public void unverifiedTokenShouldReturnUnverified() throws Exception {
        //Given unverified sessionId
        final String newEmail = "unverifiedTokenShouldReturnUnverified@CreateTokenIT";
        CreateTokenResponse ctresp = userCommands.createToken(new CreateTokenRequest(){{setEmail(newEmail);}});
        final String sessionId = ctresp.getSessionid().getSessionid();

        //When info is requested
        GetInfoResponse giresp = userCommands.getInfo(new GetInfoRequest(){{setSessionid(sessionId);}});

        //Then status is unverified
        assertEquals(Status.UNVERIFIED, giresp.getStatus());
        assertNull(giresp.getName());
        //And we have received and email
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
    }

    @Test
    public void shouldVerifyToken() throws Exception {
        //Let's ignore any previous email
        int emailPreviousIndex = greenMail.getReceivedMessages().length;

        //Given unverified sessionId
        final String newEmail = "shouldVerifyToken@CreateTokenIT";
        CreateTokenResponse ctresp = userCommands.createToken(new CreateTokenRequest(){{setEmail(newEmail);}});
        final String sessionId = ctresp.getSessionid().getSessionid();
        GetInfoResponse giresp = userCommands.getInfo(new GetInfoRequest(){{setSessionid(sessionId);}});
        Assert.isTrue(giresp.getStatus() == Status.UNVERIFIED);

        //When email is received with verification link
        Assert.isTrue(greenMail.waitForIncomingEmail(5000, 1));
        String url = extractUrlFromEmail(greenMail.getReceivedMessages()[emailPreviousIndex]);
        //We need to enforce double click as it's first time for this user, this one shows the name box
        verifyUrl(url, Response.Status.FOUND.getStatusCode());
        //this other completes with a name "user"
        URL urlParsed = new URL(url);
        URL newURL = new URL(
                urlParsed.getProtocol(),
                urlParsed.getHost(),
                urlParsed.getPort(),
                "/verifyWithUsername?" + urlParsed.getQuery() + "&username=user");

        //Then url verification responds ok
        verifyUrl(newURL.toString(), Response.Status.OK.getStatusCode());
        //And getInfo response returns username and status VERIFIED
        final GetInfoResponse giresp2 = userCommands.getInfo(new GetInfoRequest(){{setSessionid(sessionId);}});

        assertTrue(giresp2.getStatus() == Status.VERIFIED);
        assertTrue(giresp2.getName().equals("user"));
    }

    private String extractUrlFromEmail(MimeMessage content) throws IOException, MessagingException {
        String urlContent = content.getContent().toString();
        String url = urlContent.substring(0, urlContent.length() - 2);
        return url;
    }

    private void verifyUrl(String url, int code) {
        Client client = ClientBuilder.newBuilder().build();
        Response response = client.target(url).property(ClientProperties.FOLLOW_REDIRECTS, false).request().get();
        Assert.isTrue(response.getStatus() == code);
    }

}
