package com.navid.login.persistence.hibernate;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.ValidationKey;
import com.navid.login.persistence.Persistence;
import com.navid.login.persistence.hibernate.domain.SsoIdHb;
import com.navid.login.persistence.hibernate.domain.TokenHb;
import com.navid.login.persistence.hibernate.domain.UserHb;
import com.navid.login.persistence.hibernate.domain.ValidationKeyHb;
import javax.annotation.Resource;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;


import org.springframework.stereotype.Repository;

/**
 *
 * @author alberto
 */
//@Repository
public class PersistenceImplDozer implements Persistence {
    

    @Resource
    private SsoIdRepository ssoIdRepo;

    @Resource
    private TokenRepository tokenRepo;

    @Resource
    private UserRepository userRepo;

    @Resource
    private ValidationKeyRepository validationRepo;

    Mapper mapper = new DozerBeanMapper();

    public User findOneUser(String email) {
        UserHb userHb = userRepo.findOne(email);
        
        if (userHb == null ) {
            return null;
        }

        return mapper.map(userHb, User.class);
    }

    public User saveUser(User user) {
        UserHb userHb = mapper.map(user, UserHb.class);

        userHb = userRepo.save(userHb);
        
        User result = mapper.map(userHb, User.class);

        return result;
    }

    public Token findOneToken(long parseLong) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Token saveToken(Token token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SsoId saveSsoId(SsoId ssoId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ValidationKey saveValidationKey(ValidationKey validationKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ValidationKey findOneValidationKey(String validationKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteValidationKey(ValidationKey found) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
