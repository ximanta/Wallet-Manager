package com.kodilla.walletmanager.client;

import com.kodilla.walletmanager.config.AdminConfig;
import com.kodilla.walletmanager.domain.entities.ConvertCurrency;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.json.BitcoinJson;
import com.kodilla.walletmanager.json.CurrencyJson;
import com.kodilla.walletmanager.json.RatesJson;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.tools.ClassesFactory;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalAPIsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);
    private RestTemplate template;
    private AdminConfig config;
    private ClassesFactory factory;

    private ExternalAPIsClient(RestTemplate template, AdminConfig config, ClassesFactory factory) {
        this.template = template;
        this.config = config;
        this.factory = factory;
    }

    public BitcoinJson getBitcoinValue(String type){
        ResponseEntity<BitcoinJson> entity = bitcoinValueGetForEntity(type);
        if (entity.getBody() != null){
            return entity.getBody();
        }
        return factory.bitcoinJson();
    }

    public double getConvertCurrency(ConvertCurrency currency){
        double value = currency.getAmount() * convertCurrencyMechanic(currency.getFromCurrency(),currency.getToCurrency());
        return ToolsManager.tenthRoundDouble(value);
    }

    public CurrencyJson getCurrenciesValues(String type){
        ResponseEntity<CurrencyJson> entity = currenciesGetForEntity(type);
        if (entity.getBody() != null){
            return entity.getBody();
        }
        return factory.currencyJson();
    }

    public List<String> getCurrencyList(){
        return EnumSet.allOf(CurrencyType.class).stream().map(CurrencyType::name).collect(Collectors.toList());
    }

    private ResponseEntity<BitcoinJson> bitcoinValueGetForEntity(String type){
        ResponseEntity<BitcoinJson> entity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        try {
            entity = template.getForEntity(
                    config.getBitcoinApi() + type,
                    BitcoinJson.class);
            LOGGER.info("Bitcoin Value has been taken");
        } catch (RestClientException e){
            LOGGER.error("Bitcoin GET error",e);
        }
        return entity;
    }

    private ResponseEntity<CurrencyJson> currenciesGetForEntity(String type){
        ResponseEntity<CurrencyJson> entity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        try{
            entity = template.getForEntity(
                    config.getCurrencyApi() + type,
                    CurrencyJson.class);
            LOGGER.info("Currency has been taken");
        } catch (RestClientException e){
            LOGGER.error("Currency GET error",e);
        }
        return entity;
    }

    private double convertCurrencyMechanic(String fromCurrency,String toCurrency){
        try {
            CurrencyJson json = getCurrenciesValues(fromCurrency);
            Method method = RatesJson.class.getMethod("get" + toCurrency);
            double a = (Double) method.invoke(json.getRates());
            LOGGER.info("GetValues from external Api complete");
            return a;
        }catch (Exception e){
            LOGGER.error("GetValues from external Api Error",e);
            return 1;
        }
    }
}
