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
            user = persistence.saveUser(new User(email, null, null));
            LOGGER.info("User created: {}", user);
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
            throw new RuntimeException("Login no existe");
        }

        SessionId result = persistence.createSsoId(token);
        LOGGER.info("Ssoid {} created for token {}", result, token);

        return result;
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
