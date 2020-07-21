package com.kodilla.walletmanager.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalAPIsClientTest {
    @Mock
    private RestTemplate template;

    @InjectMocks
    private ExternalAPIsClient client;

    @Test
    public void bitcoinPrice() throws URISyntaxException {
        //Given
        String price = "150";
        URI uri = new URI("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=USD");

        when(template.getForEntity("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=USD",String.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        //When
        Double d = client.bitcoinPrice();
        System.out.println(d);
    }

    @Test
    public void getCurrenciesValues() {
    }
}