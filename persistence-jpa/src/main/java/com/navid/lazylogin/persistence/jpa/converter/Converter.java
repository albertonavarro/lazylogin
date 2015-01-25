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

/**
 *
 * @author casa
 */
public interface Converter {

    User convert(UserHb from);

    UserHb convert(User from);

    SessionIdHb convert(SessionId from);

    SessionId convert(SessionIdHb from);

    Token convert(TokenHb from);

    TokenHb convert(Token from);

    ValidationKey convert(ValidationKeyHb from);

    ValidationKeyHb convert(ValidationKey from);

}
