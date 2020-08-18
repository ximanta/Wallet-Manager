package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.domain.entities.ConvertCurrency;
import com.kodilla.walletmanager.json.BitcoinJson;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external")
public class ExternalAPIsController {
    private final ExternalAPIsClient client;

    private ExternalAPIsController(ExternalAPIsClient client) {
        this.client = client;
    }

    @GetMapping("/bitcoin/{type}")
    public BitcoinJson getBitcoinPrice(@PathVariable String type){
        return client.getBitcoinValue(type);
    }

    @GetMapping("/convert")
    public double getConvertValue(@RequestBody ConvertCurrency currency){
        return client.getConvertCurrency(currency);
    }

    @GetMapping("/currencyList")
    public List<String> getCurrencyList(){
        return client.getCurrencyList();
    }

}
