package com.navid.lazylogin;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.lazylogin.jetty.EmbeddedJetty;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
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
    protected GreenMail greenMail; //uses test ports by default

    @Value("${lazylogin.port}")
    private int lazyLoginPort;

    @Resource
    protected UserCommands userCommands;

    @Resource
    protected SystemCommands systemCommands;

    @Resource
    protected RequestContextContainer requestContextContainer;

    @BeforeMethod
    public void beforeTest() {
        requestContextContainer.create();
        
    }

    @AfterMethod
    public void afterTest() {
        requestContextContainer.delete();
        
    }

    @BeforeSuite
    public void init() throws Exception {        
        //helping recordserver to choose what config file should use.
        System.setProperty("env", "-ct");

        greenMail.start();
        
        WebApplicationContext context = EmbeddedJetty.runServer(lazyLoginPort);
    }

    @AfterSuite
    public void tearDown() throws Exception {

        EmbeddedJetty.stopServer();
        
        greenMail.stop();
    }

}
