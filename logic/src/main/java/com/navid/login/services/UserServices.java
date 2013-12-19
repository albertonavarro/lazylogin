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
import com.navid.login.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alberto
 */
@Service
@Transactional
public class UserServices {

    @Resource
    private JmsTemplate queue1JMSTemplate;
    
    @Resource
    private Persistence persistence;

    public void sendMessage(final String message) throws JMSException {
        queue1JMSTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    public SsoId createToken(String email) {

        User user = persistence.findOneUser(email);

        if (user == null) {
            user = persistence.saveUser(new User(email));
        }

        Token token = persistence.saveToken(new Token(user));
        ValidationKey validationKey = new ValidationKey(token, "blabla" + token.getValue());
        persistence.saveValidationKey(validationKey);

        try {
            sendMessage(validationKey.getVerificationCode());
        } catch (JMSException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        SsoId result = persistence.saveSsoId(new SsoId(token));
        return result;
    }

    @Transactional
    public SsoId loginWithToken(String tokenId) {

        Token token = persistence.findOneToken(Long.parseLong(tokenId));

        if (token == null) {
            throw new RuntimeException("Login no existe");
        }

        return persistence.saveSsoId(new SsoId(token));

    }

    public void validateKey(String validationKey) {
        ValidationKey found = persistence.findOneValidationKey(validationKey);

        if (found == null) {
            throw new RuntimeException("ValidationKey no existe");
        }

        found.getToken().setVerified(Boolean.TRUE);
        persistence.saveToken(found.getToken());
        persistence.deleteValidationKey(found);
    }

}
