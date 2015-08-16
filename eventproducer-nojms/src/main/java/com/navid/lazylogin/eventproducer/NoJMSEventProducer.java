package com.navid.lazylogin.eventproducer;

import com.navid.lazylogin.domain.ValidationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NoJMSEventProducer implements EventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoJMSEventProducer.class);

    @Resource
    private MailSender mailSender;

    @Resource
    private SimpleMailMessage templateMessage;

    @Value("${email.server.address}")
    private String emailServerAddress;


    @Override
    public void validateToken(ValidationKey validationKey) {
        LOGGER.info("Ready to send message with following parameters: {}", validationKey);

        String email = validationKey.getToken().getUser().getEmail();
        String validationUrl = emailServerAddress + "/verify/?verificationKey=" + validationKey.getValidationKey();
        String deviceInfo = null;
        sendEmail(email, validationUrl, deviceInfo);

        LOGGER.info("Email successfully sent to {}", email);
    }

    private void sendEmail(String email, String validationUrl, String deviceInfo) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText(validationUrl);
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            LOGGER.error("Error sending email", ex);
            throw ex;
        }
    }
}
