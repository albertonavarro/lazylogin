package com.navid.login.services;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.ValidationKey;
import com.navid.login.eventproducer.EventProducer;
import com.navid.login.persistence.Persistence;
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

        User user = persistence.findOneUser(email);

        if (user == null) {
            user = persistence.saveUser(new User(email, null, null));
        }
        
        Token token = persistence.createToken(user);
        
        ValidationKey validationKey = new ValidationKey(token, null);
        validationKey = persistence.saveValidationKey(validationKey);

        SsoId result = persistence.createSsoId(token);
        
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
