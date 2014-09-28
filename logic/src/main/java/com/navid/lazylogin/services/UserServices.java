package com.navid.lazylogin.services;

import com.navid.lazylogin.domain.SessionId;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);

    @Resource
    private Persistence persistence;

    @Resource
    private EventProducer eventProducer;

    public SessionId createToken(String email) {

        LOGGER.info("Creating token for {}", email);

        User user = persistence.findOneUser(email);

        if (user == null) {
            LOGGER.debug("Email not found, creating user: {}", email);
            user = persistence.saveUser(new User(email, null));
            LOGGER.info("User created: {}", user);
        } else {
            LOGGER.info("User found: {}", user);
        }

        Token token = persistence.createToken(user);
        LOGGER.info("Token created: {}", token);

        ValidationKey validationKey = new ValidationKey(token, null);
        validationKey = persistence.saveValidationKey(validationKey);
        LOGGER.info("ValidationKey created: {}", validationKey);

        SessionId result = persistence.createSsoId(token);
        LOGGER.info("SessionId created: {}", result);

        eventProducer.validateToken(validationKey);

        return result;
    }

    public SessionId loginWithToken(String tokenId) {
        LOGGER.info("Logging with token: {}", tokenId);

        Token token = persistence.findOneToken(tokenId);

        if (token == null) {
            LOGGER.info("Token not found: {}", tokenId);
            throw new IllegalArgumentException("Token doesn't exist");
        }

        SessionId result = persistence.createSsoId(token);
        LOGGER.info("Ssoid {} created for token {}", result, token);

        return result;
    }

    public Token verify(String verificationKey) throws UsernameNotFoundException {
        LOGGER.info("Verifying key: {}", verificationKey);
        
        ValidationKey found = persistence.findOneValidationKey(verificationKey);

        if (found == null) {
            throw new IllegalArgumentException("ValidationKey doesn't exist");
        } 
        
        LOGGER.info("ValidationKey found: {}", found);
        LOGGER.info("User found: {}", found.getToken().getUser());
        
        if (found.getToken().getUser().getName() == null) {
            throw new UsernameNotFoundException();
        }

        found.getToken().setValidated(Boolean.TRUE);
        Token token = persistence.saveToken(found.getToken());
        persistence.deleteValidationKey(found);
        return found.getToken();
    }

    public Token verify(String verificationKey, String username) {
        LOGGER.info("Verifying key: {} and username: {}", verificationKey, username);

        ValidationKey found = persistence.findOneValidationKey(verificationKey);

        if (found == null) {
            throw new IllegalArgumentException("ValidationKey doesn't exist");
        } 
        
        LOGGER.info("ValidationKey found: {}", found);

        if (found.getToken().getUser().getName() != null) {
            throw new IllegalArgumentException("Username already set");
        }
        
        found.getToken().getUser().setName(username);
        
        persistence.saveUser(found.getToken().getUser());
        
        found.getToken().setValidated(Boolean.TRUE);
        
        Token token = persistence.saveToken(found.getToken());
        
        LOGGER.info("Just in case: token: {}", token);
        LOGGER.info("Another case: user: {}", persistence.findOneUserByEmail(token.getUser().getEmail()));
        
        persistence.deleteValidationKey(found);
        return found.getToken();
    }

}
