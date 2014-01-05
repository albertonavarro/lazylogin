/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.login.eventproducer;

import com.navid.login.domain.Token;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 *
 * @author alberto
 */
@Component
public class AMQEventProducer implements EventProducer {
    
    @Resource
    private JmsTemplate template;

    public void validateToken(Token token) {
        template.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage("texto");
                    message.setIntProperty("messageCount", 1);
                    
                    return message;
                }
            });
    }

}
