package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.config.AdminConfig;
import com.kodilla.walletmanager.domain.pojos.Mail;
import com.kodilla.walletmanager.service.transaction.SimpleEmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private final SimpleEmailService emailService;
    private final UserService userService;
    private final AdminConfig config;

    private static final String SUBJECT = "Wallet Manager: Your week budget";

    public EmailScheduler(SimpleEmailService emailService, UserService userService, AdminConfig config) {
        this.emailService = emailService;
        this.userService = userService;
        this.config = config;
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void sendInformation(){
        double budget = userService.getAll().get(0).getBalance();
        emailService.send(new Mail(
                config.getAdminMail(),
                SUBJECT,
                "Your current budget is :" + budget + "$"));
    }
}
