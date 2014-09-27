package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.Persistence;
import com.navid.lazylogin.persistence.jpa.converter.Converter;
import com.navid.lazylogin.persistence.jpa.domain.SessionIdHb;
import com.navid.lazylogin.persistence.jpa.domain.TokenHb;
import com.navid.lazylogin.persistence.jpa.domain.UserHb;
import com.navid.lazylogin.persistence.jpa.domain.ValidationKeyHb;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alberto
 */
@Repository
public class PersistenceImpl implements Persistence {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(PersistenceImpl.class);
    
    public PersistenceImpl() {
        
    }
    

    @Resource
    private SsoIdRepository ssoIdRepo;

    @Resource
    private TokenRepository tokenRepo;

    @Resource
    private UserRepository userRepo;

    @Resource
    private ValidationKeyRepository validationRepo;
    
    @Resource
    private Queries queries;

    @Resource(name = "lazylogin.persistence.jdtoconverter")
    private Converter converter;

    @Override
    public User findOneUser(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);

        return converter.convert(userHb);
    }

    @Override
    public User saveUser(User user) {
        UserHb userHb = converter.convert(user);

        userHb = userRepo.save(userHb);
        
        User result = converter.convert(userHb);
        
        return result;
    }

    @Override
    public Token findOneToken(String token) {
        TokenHb tokenHb = tokenRepo.findOne(token);

        return converter.convert(tokenHb);
    }

    @Override
    public Token saveToken(Token token) {
        LOGGER.info("Saving user: {}", token.getUser());
        TokenHb tokenHb = converter.convert(token);
        
        LOGGER.info("Converted user: {}", tokenHb.getUser());

        tokenHb = tokenRepo.save(tokenHb);
        
        LOGGER.info("Saved user: {}", tokenHb.getUser());

        Token result = converter.convert(tokenHb);
        
        LOGGER.info("Reconverted user: {}", result.getUser());
        return result;
    }

    @Override
    public SessionId saveSsoId(SessionId ssoId) {
        SessionIdHb ssoIdHb = converter.convert(ssoId);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return converter.convert(ssoIdHb);
    }

    @Override
    public ValidationKey saveValidationKey(ValidationKey validationKey) {
        ValidationKeyHb validationKeyHb = converter.convert(validationKey);

        validationKeyHb = validationRepo.save(validationKeyHb);

        return converter.convert(validationKeyHb);
    }

    @Override
    public ValidationKey findOneValidationKey(String validationKey) {
        ValidationKeyHb validationKeyHb = validationRepo.findOne(validationKey);

        return converter.convert(validationKeyHb);
    }

    @Override
    public void deleteValidationKey(ValidationKey found) {
        ValidationKeyHb validationKeyHb = converter.convert(found);

        validationRepo.delete(validationKeyHb);
    }
    
    @Override
    public Token createToken(User user) {
        UserHb userHb = converter.convert(user);
        
        TokenHb tokenHb = new TokenHb();
        tokenHb.setUser(userHb);
        tokenHb.setValidated(false);

        tokenHb = tokenRepo.save(tokenHb);

        return converter.convert(tokenHb);
    }

    @Override
    public SessionId createSsoId(Token token) {
        TokenHb tokenHb = converter.convert(token);
        
        SessionIdHb ssoIdHb = new SessionIdHb();
        ssoIdHb.setToken(tokenHb);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return converter.convert(ssoIdHb);
        
    }

    @Override
    public SessionId findOneSessionId(String sessionId) {
        SessionIdHb ssoIdHb = ssoIdRepo.findOne(sessionId);
        return converter.convert(ssoIdHb);
    }

    @Override
    public User findOneUserByEmail(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);
        
        return converter.convert(userHb);
    }

}
