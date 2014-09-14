package com.navid.lazylogin.controller;

import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.services.UserServices;
import com.navid.lazylogin.services.UsernameNotFoundException;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(VerificationController.class);

    @Resource
    private UserServices userServices;

    @RequestMapping(value="/verify", method = RequestMethod.GET)
    public String verify(
            @RequestParam(value="verificationKey", required=true) String verificationKey, 
            ModelMap model) {

        LOGGER.info("Verifying with verificationKey {}", verificationKey);
        
        Token token;
        try {
            token = userServices.verify(verificationKey);
        } catch (UsernameNotFoundException ex) {
            return "redirect:/static/username.html";
        }

        return "validated";
    }
    
    @RequestMapping(value="/verify", method = RequestMethod.GET)
    public String validateInputWithUsername(
            @RequestParam(value="verificationKey", required=true) String verificationKey, 
            @RequestParam(value="username", required=true) String username, 
            ModelMap model) {

        LOGGER.info("Verifying with input {}", verificationKey);
        
        Token token = userServices.verify(verificationKey, username);

        return "validated";
    }
}
