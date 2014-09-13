package com.navid.lazylogin.endpoints;

import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.services.UserServices;
import com.navid.lazylogin.services.UsernameNotFoundException;
import java.net.URI;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.RedirectionException;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest")
public class RestValidatorImpl {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(RestValidatorImpl.class);

    @Resource
    private UserServices userServices;

    @GET
    @Path("/validate/{verificationKey}")
    @Produces("text/plain")
    @Descriptions({
        @Description(value = "Validates input", target = DocTarget.METHOD),
        @Description(value = "Human readable message", target = DocTarget.RETURN),
        @Description(value = "Request", target = DocTarget.REQUEST),
        @Description(value = "Response", target = DocTarget.RESPONSE),
        @Description(value = "Resource", target = DocTarget.RESOURCE)
    })
    public String validateInput(@PathParam("verificationKey") String input) {

        LOGGER.info("Verifying with input {}", input);
        
        Token token;
        try {
            token = userServices.validateKey(input);
        } catch (UsernameNotFoundException ex) {
            throw new RedirectionException(302, URI.create("/static/username.html"));
        }

        return token.getUser().getEmail() + " validated for that device.";
    }
    
    @POST
    @Path("/validate/{input}")
    @Produces("text/plain")
    @Descriptions({
        @Description(value = "Validates input", target = DocTarget.METHOD),
        @Description(value = "Human readable message", target = DocTarget.RETURN),
        @Description(value = "Request", target = DocTarget.REQUEST),
        @Description(value = "Response", target = DocTarget.RESPONSE),
        @Description(value = "Resource", target = DocTarget.RESOURCE)
    })
    public String validateInputWithUsername(@PathParam("input") String input, @FormParam("username") String username) {

        LOGGER.info("Verifying with input {}", input);
        
        Token token = userServices.validateKey(input, username);

        return token.getUser().getEmail() + " validated for that device.";
    }
}
