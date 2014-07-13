package com.navid.lazylogin.services;

import com.navid.lazylogin.domain.SsoId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.eventproducer.EventProducer;
import com.navid.lazylogin.persistence.Persistence;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alberto
 */
@Service
@Transactional
public class UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    @Resource
    private Persistence persistence;
    
    @Resource
    private EventProducer eventProducer;

    public SsoId createToken(String email) {

        logger.info("Creating token for %s", email);
        
        User user = persistence.findOneUser(email);

        if (user == null) {
            logger.debug("Email not found, creating user: %s", email);
            user = persistence.saveUser(new User(email, null, null));
            logger.info("User created: %s", user);
        }
        
        Token token = persistence.createToken(user);
        logger.info("Token created: %s", token);
        
        ValidationKey validationKey = new ValidationKey(token, null);
        validationKey = persistence.saveValidationKey(validationKey);
        logger.info("ValidationKey created: %s", validationKey);

        SsoId result = persistence.createSsoId(token);
        logger.info("SessionId created: %s", result);
        
        eventProducer.validateToken(validationKey);
        
        return result;
    }

    public SsoId loginWithToken(String tokenId) {

        Token token = persistence.findOneToken(tokenId);

        if (token == null) {
            throw new RuntimeException("Login no existe");
        }

        return persistence.createSsoId(token);

    }

    public Token validateKey(String validationKey) {
        ValidationKey found = persistence.findOneValidationKey(validationKey);

        if (found == null) {
            throw new RuntimeException("ValidationKey no existe");
        }

        found.getToken().setValidated(Boolean.TRUE);
        Token token = persistence.saveToken(found.getToken());
        persistence.deleteValidationKey(found);
        return found.getToken();
    }

}
