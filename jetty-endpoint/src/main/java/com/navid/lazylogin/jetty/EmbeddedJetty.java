package com.navid.lazylogin.jetty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.servlet.Servlet;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.jasper.servlet.JspServlet;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler.Context;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EmbeddedJetty {

    private static final int DEFAULT_PORT = 8080;
    private static final String CXF_CONTEXT_PATH = "/";
    private static final String MVC_CONTEXT_PATH = "/mvc";
    private static final String STATIC_CONTEXT_PATH = "/static";
    private static final String CONFIG_LOCATION = "classpath:conf/config-root.xml";
    private static final String CXF_MAPPING_URL = "/*";
    private static final String MVC_MAPPING_URL = "/mvc/*";
    private static final String STATIC_MAPPING_URL = "/static/*";
    private static Server server;
    private static XmlWebApplicationContext context;
    private static ContextLoaderListener listener;

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(getPortFromArgs(args));
        server.join();
    }

    public static WebApplicationContext runServer(final int port) throws Exception {

        new EmbeddedJetty().startJetty(port);
        return context;
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    public void startJetty(int port) throws Exception {
        server = new Server(port);

        HandlerList handlers = new HandlerList();
        initContext();
        handlers.setHandlers(new Handler[]{
            getServletStaticContextHandler(),
            getCxfServletContextHandler(context, listener),
            });

        server.setHandler(handlers);
        server.start();
    }

    public static void stopServer() throws Exception {
        if (server != null) {
            server.stop();
        }

    }

    private static ServletContextHandler getCxfServletContextHandler(WebApplicationContext context, ContextLoaderListener listener) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CXF_CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MVC_MAPPING_URL);

        contextHandler.addServlet(new ServletHolder(new CXFServlet()), CXF_MAPPING_URL);
        //contextHandler.addServlet(JspServlet.class, "/jsp/*");
        contextHandler.addEventListener(listener);
        contextHandler.setResourceBase(new ClassPathResource("/jsp").getURI().toString());

        return contextHandler;
    }
    
    private static Handler getServletStaticContextHandler() throws IOException, URISyntaxException {
        ResourceHandler resHandler = new ResourceHandler();
        resHandler.setResourceBase(Resource.newClassPathResource("/static").toString());
        ContextHandler ctx = new ContextHandler("/static"); /* the server uri path */

        ctx.setHandler(resHandler);
        return ctx;
    }

    private static void initContext() {
        context = new XmlWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        listener = new ContextLoaderListener(context);
    }

}
