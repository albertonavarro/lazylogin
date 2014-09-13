package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.Persistence;
import com.navid.lazylogin.persistence.hibernate.converter.Converter;
import com.navid.lazylogin.persistence.hibernate.domain.SessionIdHb;
import com.navid.lazylogin.persistence.hibernate.domain.TokenHb;
import com.navid.lazylogin.persistence.hibernate.domain.UserHb;
import com.navid.lazylogin.persistence.hibernate.domain.ValidationKeyHb;
import javax.annotation.Resource;
import org.jdto.DTOBinder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alberto
 */
@Repository
public class PersistenceImpl implements Persistence {
    

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

    @Resource
    private Converter converter;

    public User findOneUser(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);

        return converter.convert(userHb);
    }

    public User saveUser(User user) {
        UserHb userHb = converter.convert(user);

        userHb = userRepo.save(userHb);
        
        User result = converter.convert(userHb);
        
        return result;
    }

    public Token findOneToken(String token) {
        TokenHb tokenHb = tokenRepo.findOne(token);

        return converter.convert(tokenHb);
    }

    public Token saveToken(Token token) {
        TokenHb tokenHb = converter.convert(token);

        tokenHb = tokenRepo.save(tokenHb);

        return converter.convert(tokenHb);
    }

    public SessionId saveSsoId(SessionId ssoId) {
        SessionIdHb ssoIdHb = converter.convert(ssoId);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return converter.convert(ssoIdHb);
    }

    public ValidationKey saveValidationKey(ValidationKey validationKey) {
        ValidationKeyHb validationKeyHb = converter.convert(validationKey);

        validationKeyHb = validationRepo.save(validationKeyHb);

        return converter.convert(validationKeyHb);
    }

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

    public SessionId createSsoId(Token token) {
        TokenHb tokenHb = converter.convert(token);
        
        SessionIdHb ssoIdHb = new SessionIdHb();
        ssoIdHb.setToken(tokenHb);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return converter.convert(ssoIdHb);
        
    }

    public SessionId findOneSessionId(String sessionId) {
        SessionIdHb ssoIdHb = ssoIdRepo.findOne(sessionId);
        return converter.convert(ssoIdHb);
    }

    public User findOneUserByEmail(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);
        
        return converter.convert(userHb);
    }

}
