package com.navid.lazylogin.services;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;


public interface UserServices {

    SessionId createToken(String email);

    SessionId loginWithToken(String tokenId);

    Token verify(String verificationKey) throws UsernameNotFoundException;

    Token verify(String verificationKey, String username);
    
}
