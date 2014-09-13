/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.lazylogin.persistence.hibernate.converter;

import com.navid.lazylogin.domain.SessionId;
import com.navid.lazylogin.domain.Token;
import com.navid.lazylogin.domain.User;
import com.navid.lazylogin.domain.ValidationKey;
import com.navid.lazylogin.persistence.hibernate.domain.SessionIdHb;
import com.navid.lazylogin.persistence.hibernate.domain.TokenHb;
import com.navid.lazylogin.persistence.hibernate.domain.UserHb;
import com.navid.lazylogin.persistence.hibernate.domain.ValidationKeyHb;
import javax.annotation.Resource;
import org.jdto.DTOBinder;

/**
 *
 * @author casa
 */
public class JdtoConverter implements Converter {

    private DTOBinder binder;
    
    public JdtoConverter(DTOBinder binder){
        this.binder = binder;
    }

    @Override
    public User convert(UserHb from) {
        User user = binder.bindFromBusinessObject(User.class, from);
        user.setName(from.getUsername()); //jdto doesn't allow immutable and fields at the same time
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
        return binder.bindFromBusinessObject(Token.class, from);
    }

    @Override
    public TokenHb convert(Token from) {
        return binder.bindFromBusinessObject(TokenHb.class, from);
    }

    @Override
    public ValidationKey convert(ValidationKeyHb from) {
        return binder.bindFromBusinessObject(ValidationKey.class, from);
    }

    @Override
    public ValidationKeyHb convert(ValidationKey from) {
        return binder.bindFromBusinessObject(ValidationKeyHb.class, from);
    }
    
}
