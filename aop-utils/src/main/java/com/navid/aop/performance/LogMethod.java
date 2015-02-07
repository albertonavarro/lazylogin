package com.navid.aop.performance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Created by casa on 31/01/15.
 */
public class LogMethod implements MethodInterceptor {
    
    private static final Logger LOG = LoggerFactory.getLogger(LogMethod.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        StopWatch monitor = new StopWatch();
        monitor.start("monitor");
        boolean error = false;
        Object returned = null;
        try{
            returned = methodInvocation.proceed();
            monitor.stop();
            return returned;
        } catch (Exception e) {
            monitor.stop();
            error = true;
            throw e;
        } finally {
            LOG.info("{} {} {} {}", error? "ERROR" : "SUCCESS", monitor.getTotalTimeSeconds(),  methodInvocation.getMethod().getDeclaringClass().getCanonicalName(), methodInvocation.getMethod().getName(), methodInvocation.getMethod());
        }
    }
}
