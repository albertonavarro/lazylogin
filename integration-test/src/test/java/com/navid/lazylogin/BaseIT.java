package com.navid.lazylogin;

import com.navid.lazylogin.context.RequestContextContainer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @BeforeClass
    public void init() throws Exception {
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

}
