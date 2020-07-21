package com.kodilla.walletmanager.client;

import com.kodilla.walletmanager.json.CurrencyJson;
import com.kodilla.walletmanager.json.RatesJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalAPIsClientTest {
    @Mock
    private RestTemplate template;

    @InjectMocks
    private ExternalAPIsClient client;

    @Test
    public void bitcoinPrice() {

    }

    @Test
    public void getCurrenciesValues() {
        //Given
        CurrencyJson currencyJson = new CurrencyJson();
        currencyJson.setDate("2020-05-05");
        currencyJson.setBase("USD");
        currencyJson.setRates(new RatesJson());

        ResponseEntity<CurrencyJson> entity = new ResponseEntity<>(currencyJson, HttpStatus.OK);

        //When
        when(template.getForEntity(
                "https://api.exchangeratesapi.io/latest?base=USD",
                CurrencyJson.class)).thenReturn(entity);
        CurrencyJson json = client.getCurrenciesValues();

        //Then
        assertEquals("2020-05-05",json.getDate());
        assertEquals("USD",json.getBase());
        assertNotNull(json.getRates());
    }
}