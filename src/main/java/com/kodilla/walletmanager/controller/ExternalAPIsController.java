package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.json.CurrencyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
public class ExternalAPIsController {
    @Autowired
    ExternalAPIsClient client;

    @GetMapping("/bitcoin")
    public double getBitcoinPrice(){
        return client.bitcoinPrice();
    }

    @GetMapping("/currency")
    public CurrencyJson getCurrenciesValues(){
        return client.getCurrenciesValues();
    }

}
