package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.domain.entities.ConvertCurrency;
import com.kodilla.walletmanager.json.BitcoinJson;
import com.kodilla.walletmanager.json.CurrencyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
public class ExternalAPIsController {
    private final ExternalAPIsClient client;

    private ExternalAPIsController(ExternalAPIsClient client) {
        this.client = client;
    }

    @GetMapping("/bitcoin/{type}")
    public BitcoinJson getBitcoinPrice(@PathVariable String type){
        return client.bitcoinValue(type);
    }

    @GetMapping("/convert")
    public double getConvertValue(@RequestBody ConvertCurrency currency){
        return client.getConvertCurrency(currency);
    }

}
