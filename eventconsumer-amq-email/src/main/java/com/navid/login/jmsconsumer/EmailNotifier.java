package com.navid.login.jmsconsumer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author alberto
 */
public class EmailNotifier implements MessageListener {

    @Resource
    private MailSender mailSender;
    
    @Resource
    private SimpleMailMessage templateMessage;

    @Override
    public void onMessage(Message msg) {
        try {
            MapMessage mapMessage = (MapMessage) msg;
            String email = mapMessage.getString("email");
            String validationUrl = mapMessage.getString("validationUrl");
            String deviceInfo = mapMessage.getString("deviceInfo");
            sendEmail(email, validationUrl, deviceInfo);
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    private void sendEmail(String email, String validationUrl, String deviceInfo) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText(validationUrl);
        try{
            this.mailSender.send(msg);
        }
        catch(MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());            
        }
    }

}
