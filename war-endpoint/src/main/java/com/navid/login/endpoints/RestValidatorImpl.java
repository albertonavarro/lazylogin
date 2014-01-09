package com.navid.login.endpoints;

import com.navid.login.domain.Token;
import com.navid.login.services.UserServices;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/rest")
public class RestValidatorImpl {
    
    @Resource
    private UserServices userServices;

    @GET
    @Path("/validate/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        
        Token token = userServices.validateKey(input);
        
        return token.getUser().getEmail() + " validated for that device.";
    }
}