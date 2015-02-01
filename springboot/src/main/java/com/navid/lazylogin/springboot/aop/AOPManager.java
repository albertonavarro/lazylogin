package com.navid.lazylogin.springboot.aop;

import com.navid.lazylogin.persistence.Persistence;
import com.navid.lazylogin.services.UserServices;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Configuration
@ManagedResource(objectName = "spring:name=simpleBean", description = "A sample JMX-managed bean")
public class AOPManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final Logger LOG = LoggerFactory.getLogger(AOPManager.class);

    private static final LogMethod LOG_METHOD = new LogMethod();

    @PostConstruct
    public void setUp() {
        for(Advised advised : applicationContext.getBeansOfType(Advised.class).values()) {
            advised.addAdvice(0, LOG_METHOD);
        }
    }

    @ManagedOperation
    public Object[] getAdvisableClasses() {
        return applicationContext.getBeansOfType(Advised.class).keySet().toArray();
    }
    @ManagedOperation
    public void adviseBean(String bean) throws ClassNotFoundException {
        ((Advised) applicationContext.getBean(bean)).addAdvice(LOG_METHOD);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
