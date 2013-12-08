/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.login.services;

import com.navid.login.domain.SsoId;
import com.navid.login.domain.Token;
import com.navid.login.domain.User;
import com.navid.login.domain.ValidationKey;
import com.navid.login.persistence.SsoIdRepository;
import com.navid.login.persistence.TokenRepository;
import com.navid.login.persistence.UserRepository;
import com.navid.login.persistence.ValidationKeyRepository;
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

    @Resource
    private ValidationKeyRepository validationRepo;

    public SsoId createToken(String email) {

        User user = userRepo.findOne(email);

        if (user == null) {
            user = userRepo.save(new User(email));
        }

        Token token = tokenRepo.save(new Token(user));
        ValidationKey validationKey = new ValidationKey(token, "blabla" + token.getValue());
        validationRepo.save(validationKey);

        return ssoIdRepo.save(new SsoId(token));
    }

    public SsoId loginWithToken(String tokenId) {

        Token token = tokenRepo.findOne(Long.parseLong(tokenId));

        if (token == null) {
            throw new RuntimeException("Login no existe");
        }

        return ssoIdRepo.save(new SsoId(token));

    }

    public void validateKey(String validationKey) {
        ValidationKey found = validationRepo.findOne(validationKey);
        
        if (found == null) {
            throw new RuntimeException("ValidationKey no existe");
        }
        
        found.getToken().setVerified(Boolean.TRUE);
        tokenRepo.save(found.getToken());
        validationRepo.delete(found);
    }

}
