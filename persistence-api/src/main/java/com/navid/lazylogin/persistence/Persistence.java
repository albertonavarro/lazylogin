package com.navid.lazylogin.persistence;

import com.navid.lazylogin.domain.SessionId;
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
    
    SessionId saveSsoId(SessionId ssoId);

    ValidationKey saveValidationKey(ValidationKey validationKey);

    ValidationKey findOneValidationKey(String validationKey);

    void deleteValidationKey(ValidationKey found);
    
    Token createToken(User user);

    SessionId createSsoId(Token token);

    SessionId findOneSessionId(String sessionId);
    
    User findOneUserByEmail(String email);
    
}
