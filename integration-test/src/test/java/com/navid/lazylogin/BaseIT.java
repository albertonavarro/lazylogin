package com.navid.lazylogin;

import com.navid.lazylogin.context.RequestContextContainer;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 *
 * @author vero
 */
@Test
@ContextConfiguration(locations = {"classpath:conf/test-lazylogin-client.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);

    @Resource
    protected UserCommands userCommands;

    @Resource
    protected SystemCommands systemCommands;

    @Resource
    protected RequestContextContainer requestContextContainer;
    
    @Value("${test.imap.username}")
    protected String testEmail;
    
    @Value("${test.imap.password}")
    private String testPassword;
    
    @Value("${test.imap.address}")
    private String imapHost;
    
    private final int maxRetries = 10;
    
    Folder inbox;
            
    private int emailIndex;

    @BeforeMethod
    public void beforeTest() throws MessagingException {
        requestContextContainer.create();
        emailIndex = inbox.getMessageCount();
    }

    @AfterMethod
    public void afterTest() {
        requestContextContainer.delete();
    }

    @BeforeClass
    public void init() throws Exception {
        initGmail();
    }

    @AfterClass
    public void tearDown() throws Exception {
    }
    
    private void initGmail() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(imapHost, testEmail, testPassword);

            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            
            
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    protected String getUrlFromEmail() throws MessagingException, IOException, InterruptedException {
        int retries = 0;
        while(retries++ < maxRetries && inbox.getMessageCount() == emailIndex) {
            System.out.println("Email hasn't arrived yet, waiting another second. Retries: " + retries);
            Thread.sleep(1000L);
        }
        
        if(inbox.getMessageCount() == emailIndex) {
            throw new IllegalStateException("Email not received");
        }
        
        Message message = inbox.getMessage(emailIndex + 1); //this library/protocol is not zero based
        String content = message.getContent().toString();
        String url = content.substring(0, content.length() -2);
        return url;
    }
    
    protected void verifyUrl(String url) {
        Client client = ClientBuilder.newBuilder().build();
        Response response = client.target(url).request().get();
        Assert.isTrue(HttpStatus.isSuccess(response.getStatus()));
    }
}
