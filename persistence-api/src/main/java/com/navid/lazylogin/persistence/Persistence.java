package com.navid.lazylogin.persistence;

import com.navid.lazylogin.domain.SsoId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;

/**
 *
 * @author alberto
 */
public interface Persistence {

    User findOneUser(String email);
    
    User saveUser(User user);
    
    Token findOneToken(String token);

    Token saveToken(Token token);
    
    SsoId saveSsoId(SsoId ssoId);

    ValidationKey saveValidationKey(ValidationKey validationKey);

    ValidationKey findOneValidationKey(String validationKey);

    void deleteValidationKey(ValidationKey found);
    
    Token createToken(User user);

    SsoId createSsoId(Token token);

    SsoId findOneSessionId(String sessionId);
    
    User findOneUserByEmail(String email);
    
}
