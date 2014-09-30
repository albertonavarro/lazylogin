package com.navid.lazylogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({"classpath:/conf/config-web-services.xml", "classpath:conf/config-amq.xml", "classpath:conf/config-amq-email.xml"})
@PropertySource(value={"classpath:/application.properties", "classpath:/conf/lazylogin${env}.overrides", "file:${user.home}/navidconfig/lazylogin${env}.overrides" }, ignoreResourceNotFound = true)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        org.apache.cxf.transport.servlet.CXFServlet cxfServlet = new org.apache.cxf.transport.servlet.CXFServlet();
        ServletRegistrationBean servletDef = new ServletRegistrationBean(cxfServlet, "/services/*");
        servletDef.setLoadOnStartup(1);
        return servletDef;
    }
    
    /*
    <bean id="configuration" class="com.navid.utils.CustomPropertySourcesPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath:/application.properties</value>
                <value>classpath:/conf/lazylogin.defaults</value>
                <value>classpath:/conf/lazylogin#{ systemProperties['env']}.overrides</value>
                <value>file:${user.home}/navidconfig/lazylogin#{ systemProperties['env']}.overrides</value>
            </list>
        </property>
        <property name="systemPropertiesModeName" value="SYSTEM_PROPERTIES_MODE_OVERRIDE"/>
        <property name="ignoreResourceNotFound" value="true"/>
        <property name="ignoreUnresolvablePlaceholders" value="false"/>
        <property name="fileEncoding" value="UTF-8" />
    </bean>*/

}
