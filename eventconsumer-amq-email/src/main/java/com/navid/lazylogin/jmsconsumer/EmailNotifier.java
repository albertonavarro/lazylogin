package com.navid.lazylogin.jmsconsumer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author alberto
 */
public class EmailNotifier implements MessageListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotifier.class);

    @Resource
    private MailSender mailSender;
    
    @Resource
    private SimpleMailMessage templateMessage;

    @Override
    public void onMessage(Message msg) {
        try {
            LOGGER.info("Ready to send message with following parameters: {}", msg);
            
            MapMessage mapMessage = (MapMessage) msg;
            
            String email = mapMessage.getString("email");
            String validationUrl = mapMessage.getString("validationUrl");
            String deviceInfo = mapMessage.getString("deviceInfo");
            sendEmail(email, validationUrl, deviceInfo);
            
            LOGGER.info("Email successfully sent");
        } catch (JMSException jmse) {
            LOGGER.error("Error processing message from queue", jmse);
        }
    }

    private void sendEmail(String email, String validationUrl, String deviceInfo) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText(validationUrl);
        try{
            this.mailSender.send(msg);
        } catch(MailException ex) {
            LOGGER.error("Error sending email", ex);
        }
    }

}
