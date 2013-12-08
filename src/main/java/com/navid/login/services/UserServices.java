/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.login.services;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.persistence.SsoIdRepository;
import com.navid.login.persistence.TokenRepository;
import com.navid.login.persistence.UserRepository;
import javax.annotation.Resource;

/**
 *
 * @author alberto
 */
public class UserServices {

    @Resource
    private SsoIdRepository ssoIdRepo;

    @Resource
    private TokenRepository tokenRepo;
    
    @Resource
    private UserRepository userRepo;
    
    public SsoId createToken(String email) {
        
        User user = userRepo.findOne(email);
        
        if (user == null){
            user = userRepo.save(new User(email));
        }
        
        Token token = tokenRepo.save(new Token(user));
        
        return ssoIdRepo.save(new SsoId(token));
    }
    
    public SsoId loginWithToken(String tokenId) {
        
        Token token = tokenRepo.findOne(Long.parseLong(tokenId));
        
        if ( token == null ){
            throw new RuntimeException("Login no existe");
        }
        
        return ssoIdRepo.save(new SsoId(token));
        
    }

}
