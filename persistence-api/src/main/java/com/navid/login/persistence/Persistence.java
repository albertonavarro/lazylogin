package com.navid.login.persistence;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.ValidationKey;

/**
 *
 * @author alberto
 */
public interface Persistence {

    User findOneUser(String email);
    
    User saveUser(User user);
    
    Token findOneToken(long parseLong);

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
