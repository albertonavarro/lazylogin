package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.Persistence;
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

    @Resource(name = "lazylogin.persistence-hibernate.converter")
    private DTOBinder binder;

    public User findOneUser(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);

        return binder.bindFromBusinessObject(User.class, userHb);
    }

    public User saveUser(User user) {
        UserHb userHb = binder.bindFromBusinessObject(UserHb.class, user);

        userHb = userRepo.save(userHb);
        
        User result = binder.bindFromBusinessObject(User.class, userHb);
        
        return result;
    }

    public Token findOneToken(String token) {
        TokenHb tokenHb = tokenRepo.findOne(token);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public Token saveToken(Token token) {
        TokenHb tokenHb = binder.bindFromBusinessObject(TokenHb.class, token);

        tokenHb = tokenRepo.save(tokenHb);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public SessionId saveSsoId(SessionId ssoId) {
        SessionIdHb ssoIdHb = binder.bindFromBusinessObject(SessionIdHb.class, ssoId);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return binder.bindFromBusinessObject(SessionId.class, ssoIdHb);
    }

    public ValidationKey saveValidationKey(ValidationKey validationKey) {
        ValidationKeyHb validationKeyHb = binder.bindFromBusinessObject(ValidationKeyHb.class, validationKey);

        validationKeyHb = validationRepo.save(validationKeyHb);

        return binder.bindFromBusinessObject(ValidationKey.class, validationKeyHb);
    }

    public ValidationKey findOneValidationKey(String validationKey) {
        ValidationKeyHb validationKeyHb = validationRepo.findOne(validationKey);

        return binder.bindFromBusinessObject(ValidationKey.class, validationKeyHb);
    }

    public void deleteValidationKey(ValidationKey found) {
        ValidationKeyHb validationKeyHb = binder.bindFromBusinessObject(ValidationKeyHb.class, found);

        validationRepo.delete(validationKeyHb);
    }
    
    public Token createToken(User user) {
        UserHb userHb = binder.bindFromBusinessObject(UserHb.class, user);
        
        TokenHb tokenHb = new TokenHb();
        tokenHb.setUser(userHb);
        tokenHb.setValidated(false);

        tokenHb = tokenRepo.save(tokenHb);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public SessionId createSsoId(Token token) {
        TokenHb tokenHb = binder.bindFromBusinessObject(TokenHb.class, token);
        
        SessionIdHb ssoIdHb = new SessionIdHb();
        ssoIdHb.setToken(tokenHb);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return binder.bindFromBusinessObject(SessionId.class, ssoIdHb);
        
    }

    public SessionId findOneSessionId(String sessionId) {
        SessionIdHb ssoIdHb = ssoIdRepo.findOne(sessionId);
        return binder.bindFromBusinessObject(SessionId.class, ssoIdHb);
    }

    public User findOneUserByEmail(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);
        
        return binder.bindFromBusinessObject(User.class, userHb);
    }

}
