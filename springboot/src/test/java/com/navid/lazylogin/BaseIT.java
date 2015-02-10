package com.navid.lazylogin;

import com.icegreen.greenmail.util.GreenMail;
import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.user.v0.UserCommands;
import com.navid.lazylogin.context.RequestContextContainer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
    protected GreenMail greenMail; //uses test ports by default

    @Value("${server.port}")
    private int lazyLoginPort;

    @Resource
    protected UserCommands userCommands;

    @Resource
    protected SystemCommands systemCommands;

    @Resource
    protected RequestContextContainer requestContextContainer;

    ConfigurableApplicationContext app;

    @BeforeMethod
    public void beforeTest() {

    }

    @AfterMethod
    public void afterTest() {
        requestContextContainer.delete();

    }

    @BeforeClass
    public void init() throws Exception {
        //helping recordserver to choose what config file should use.
        System.setProperty("env", "-ct");

        greenMail.start();

        app = new SpringApplication(Application.class).run(new String[0]);
    }

    @AfterClass
    public void tearDown() throws Exception {

        SpringApplication.exit(app, new ExitCodeGenerator() {

            @Override
            public int getExitCode() {
                return 0;
            }
        });

        greenMail.stop();
    }

}
