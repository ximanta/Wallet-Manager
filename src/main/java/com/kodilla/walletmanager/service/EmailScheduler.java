package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.config.AdminConfig;
import com.kodilla.walletmanager.domain.entities.Mail;
import com.kodilla.walletmanager.service.transaction.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminConfig config;

    private static final String SUBJECT = "Wallet Manager: Your week budget";

    @Scheduled(cron = "0 0 1 * * MON")
    public void sendInformation(){
        double budget = userService.getAll().get(0).getBalance();
        emailService.send(new Mail(
                config.getAdminMail(),
                SUBJECT,
                "Your current budget is :" + budget + "$"));
    }
}
