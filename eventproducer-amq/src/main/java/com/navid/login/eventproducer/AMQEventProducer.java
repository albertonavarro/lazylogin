package com.navid.login.eventproducer;

import com.navid.login.domain.ValidationKey;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MapMessage;
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

    @Override
    public void validateToken(final ValidationKey validationKey) {
        template.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();

                message.setString("email", validationKey.getToken().getUser().getEmail());
                message.setString("validationUrl", "http://localhost:8080/rest/validate/" + validationKey.getValidationKey());

                return message;
            }
        });
    }

}
