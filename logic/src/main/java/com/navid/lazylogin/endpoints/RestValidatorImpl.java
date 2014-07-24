package com.navid.lazylogin.endpoints;

import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.services.UserServices;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Path("/validate/{input}")
    @Produces("text/plain")
    @Descriptions({
        @Description(value = "Validates input", target = DocTarget.METHOD),
        @Description(value = "Human readable message", target = DocTarget.RETURN),
        @Description(value = "Request", target = DocTarget.REQUEST),
        @Description(value = "Response", target = DocTarget.RESPONSE),
        @Description(value = "Resource", target = DocTarget.RESOURCE)
    })
    public String validateInput(@PathParam("input") String input) {

        LOGGER.info("Verifying with input {}", input);
        
        Token token = userServices.validateKey(input);

        return token.getUser().getEmail() + " validated for that device.";
    }
}
