package com.kodilla.walletmanager.client;

import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.json.CurrencyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityListeners;

@Component
public class ExternalAPIsClient {
    @Autowired
    RestTemplate template;

    public double bitcoinPrice(){
        try {
            ResponseEntity<String> entity = template.getForEntity(
                    "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=USD",
                    String.class);
            return checkString(entity.getBody());
        } catch (RestClientException e){
            throw new  RuntimeException(e);
        }
    }

    public CurrencyJson getCurrenciesValues(CurrencyType type){
        try{
            ResponseEntity<CurrencyJson> entity = template.getForEntity(
                    "https://api.exchangeratesapi.io/latest?base=" + type.toString(),
                    CurrencyJson.class);
            return entity.getBody();
        } catch (RestClientException e){
            throw new  RuntimeException(e);
        }
    }

    private double checkString(String price){
        try{
            price = price.substring(
                    price.lastIndexOf(":") + 1,
                    price.lastIndexOf("}}"));
            return Double.parseDouble(price);
        } catch (RestClientException e){
            return 0;
        }
    }
}
