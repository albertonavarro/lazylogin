package com.navid.login.persistence.hibernate;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.UserId;
import com.navid.login.domain.ValidationKey;
import com.navid.login.persistence.Persistence;
import com.navid.login.persistence.hibernate.domain.SsoIdHb;
import com.navid.login.persistence.hibernate.domain.TokenHb;
import com.navid.login.persistence.hibernate.domain.UserHb;
import com.navid.login.persistence.hibernate.domain.ValidationKeyHb;
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

    public Token findOneToken(long parseLong) {
        TokenHb tokenHb = tokenRepo.findOne(parseLong);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public Token saveToken(Token token) {
        TokenHb tokenHb = binder.bindFromBusinessObject(TokenHb.class, token);

        tokenHb = tokenRepo.save(tokenHb);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public SsoId saveSsoId(SsoId ssoId) {
        SsoIdHb ssoIdHb = binder.bindFromBusinessObject(SsoIdHb.class, ssoId);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return binder.bindFromBusinessObject(SsoId.class, ssoIdHb);
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

        tokenHb = tokenRepo.save(tokenHb);

        return binder.bindFromBusinessObject(Token.class, tokenHb);
    }

    public SsoId createSsoId(Token token) {
        TokenHb tokenHb = binder.bindFromBusinessObject(TokenHb.class, token);
        
        SsoIdHb ssoIdHb = new SsoIdHb();
        ssoIdHb.setToken(tokenHb);

        ssoIdHb = ssoIdRepo.save(ssoIdHb);

        return binder.bindFromBusinessObject(SsoId.class, ssoIdHb);
        
    }

    public SsoId findOneSessionId(String sessionId) {
        SsoIdHb ssoIdHb = ssoIdRepo.findOne(sessionId);
        return binder.bindFromBusinessObject(SsoId.class, ssoIdHb);
    }

    public User findOneUserByEmail(String email) {
        UserHb userHb = queries.findSingleUserByEmail(email);
        
        return binder.bindFromBusinessObject(User.class, userHb);
    }

}
