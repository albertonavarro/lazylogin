/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.lazylogin.persistence.jpa.converter;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.jpa.domain.SessionIdHb;
import com.navid.lazylogin.persistence.jpa.domain.TokenHb;
import com.navid.lazylogin.persistence.jpa.domain.UserHb;
import com.navid.lazylogin.persistence.jpa.domain.ValidationKeyHb;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author casa
 */
@Component(value = "lazylogin.persistence.jdtoconverter")
public class JdtoConverter implements Converter {

    private final DTOBinder binder;
    
    @Autowired
    public JdtoConverter(@Qualifier(value = "lazylogin.persistence-hibernate.converter") DTOBinder binder){
        this.binder = binder;
    }

    @Override
    public User convert(UserHb from) {
        User user = binder.bindFromBusinessObject(User.class, from);
        if(from!=null){
            user.setName(from.getUsername()); //jdto doesn't allow immutable and fields at the same time
        }
        return user;
    }

    @Override
    public UserHb convert(User from) {
        return binder.bindFromBusinessObject(UserHb.class, from);
    }

    @Override
    public SessionIdHb convert(SessionId from) {
        return binder.bindFromBusinessObject(SessionIdHb.class, from);
    }

    @Override
    public SessionId convert(SessionIdHb from) {
        return binder.bindFromBusinessObject(SessionId.class, from);
    }

    @Override
    public Token convert(TokenHb from) {
        Token result = binder.bindFromBusinessObject(Token.class, from);
        if(result!= null && result.getUser() !=null){
            result.getUser().setName(from.getUser().getUsername()); //jdto doesn't allow immutable and fields at the same time
        }
        return result;
    }

    @Override
    public TokenHb convert(Token from) {
        return binder.bindFromBusinessObject(TokenHb.class, from);
    }

    @Override
    public ValidationKey convert(ValidationKeyHb from) {
        ValidationKey result = binder.bindFromBusinessObject(ValidationKey.class, from);
        if(result!= null && result.getToken()!= null && result.getToken().getUser() !=null){
            result.getToken().getUser().setName(from.getToken().getUser().getUsername()); //jdto doesn't allow immutable and fields at the same time
        }
        
        return result;
    }

    @Override
    public ValidationKeyHb convert(ValidationKey from) {
        return binder.bindFromBusinessObject(ValidationKeyHb.class, from);
    }
    
}
