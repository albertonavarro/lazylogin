package com.navid.lazylogin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({"classpath:/conf/config-web-services.xml","classpath:/conf/config-root.xml"})
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

}
