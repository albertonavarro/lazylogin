package com.navid.login.endpoints;

import com.navid.login.domain.Token;
import com.navid.login.services.UserServices;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@Path("/rest")
public class RestValidatorImpl {
    
    @Resource
    private UserServices userServices;

    @GET
    @Path("/validate/{input}")
    @Produces("text/plain")
    @Descriptions({
   @Description(value = "Adds a new book", target = DocTarget.METHOD),
   @Description(value = "Requested Book", target = DocTarget.RETURN),
   @Description(value = "Request", target = DocTarget.REQUEST),
   @Description(value = "Response", target = DocTarget.RESPONSE),
   @Description(value = "Resource", target = DocTarget.RESOURCE)
})
    public String ping(@PathParam("input") String input) {
        
        Token token = userServices.validateKey(input);
        
        return token.getUser().getEmail() + " validated for that device.";
    }
}