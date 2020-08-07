package com.kodilla.walletmanager.json;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
public class RatesJson {
    @JsonProperty("CAD")
    private Double CAD;
    @JsonProperty("HKD")
    private Double HKD;
    @JsonProperty("ISK")
    private Double ISK;
    @JsonProperty("PHP")
    private Double PHP;
    @JsonProperty("DKK")
    private Double DKK;
    @JsonProperty("HUF")
    private Double HUF;
    @JsonProperty("CZK")
    private Double CZK;
    @JsonProperty("GBP")
    private Double GBP;
    @JsonProperty("RON")
    private Double RON;
    @JsonProperty("SEK")
    private Double SEK;
    @JsonProperty("IDR")
    private Double IDR;
    @JsonProperty("INR")
    private Double INR;
    @JsonProperty("BRL")
    private Double BRL;
    @JsonProperty("RUB")
    private Double RUB;
    @JsonProperty("HRK")
    private Double HRK;
    @JsonProperty("JPY")
    private Double JPY;
    @JsonProperty("THB")
    private Double THB;
    @JsonProperty("CHF")
    private Double CHF;
    @JsonProperty("EUR")
    private Double EUR;
    @JsonProperty("MYR")
    private Double MYR;
    @JsonProperty("BGN")
    private Double BGN;
    @JsonProperty("TRY")
    private Double TRY;
    @JsonProperty("CNY")
    private Double CNY;
    @JsonProperty("NOK")
    private Double NOK;
    @JsonProperty("NZD")
    private Double NZD;
    @JsonProperty("ZAR")
    private Double ZAR;
    @JsonProperty("USD")
    private Double USD;
    @JsonProperty("MXN")
    private Double MXN;
    @JsonProperty("SGD")
    private Double SGD;
    @JsonProperty("AUD")
    private Double AUD;
    @JsonProperty("ILS")
    private Double ILS;
    @JsonProperty("KRW")
    private Double KRW;
    @JsonProperty("PLN")
    private Double PLN;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
