package com.kodilla.walletmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${link.api.currency}")
    private String currencyApi;

    @Value("${link.api.bitcoin}")
    private String bitcoinApi;
}
