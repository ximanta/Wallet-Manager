package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.pojos.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private final JavaMailSender sender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    public SimpleEmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(final Mail mail) {
        LOGGER.info("Start email preparation");
        try{
            sender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent.");
        }catch (MailException e){
            LOGGER.error("Failed to process email sending");
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }

}
