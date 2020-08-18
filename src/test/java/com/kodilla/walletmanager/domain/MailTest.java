package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.entities.Mail;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailTest {

    @Test
    public void createCompleteMail(){
        //Given When
        Mail mail = new Mail();
        mail.setMailTo("test@email.com");
        mail.setSubject("Test");
        mail.setMessage("Test Message");

        //Then
        assertEquals("test@email.com",mail.getMailTo());
        assertEquals("Test",mail.getSubject());
        assertEquals("Test Message",mail.getMessage());
    }

}