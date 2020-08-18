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
    private double CAD;
    @JsonProperty("HKD")
    private double HKD;
    @JsonProperty("ISK")
    private double ISK;
    @JsonProperty("PHP")
    private double PHP;
    @JsonProperty("DKK")
    private double DKK;
    @JsonProperty("HUF")
    private double HUF;
    @JsonProperty("CZK")
    private double CZK;
    @JsonProperty("GBP")
    private double GBP;
    @JsonProperty("RON")
    private double RON;
    @JsonProperty("SEK")
    private double SEK;
    @JsonProperty("IDR")
    private double IDR;
    @JsonProperty("INR")
    private double INR;
    @JsonProperty("BRL")
    private double BRL;
    @JsonProperty("RUB")
    private double RUB;
    @JsonProperty("HRK")
    private double HRK;
    @JsonProperty("JPY")
    private double JPY;
    @JsonProperty("THB")
    private double THB;
    @JsonProperty("CHF")
    private double CHF;
    @JsonProperty("EUR")
    private double EUR;
    @JsonProperty("MYR")
    private double MYR;
    @JsonProperty("BGN")
    private double BGN;
    @JsonProperty("TRY")
    private double TRY;
    @JsonProperty("CNY")
    private double CNY;
    @JsonProperty("NOK")
    private double NOK;
    @JsonProperty("NZD")
    private double NZD;
    @JsonProperty("ZAR")
    private double ZAR;
    @JsonProperty("USD")
    private double USD;
    @JsonProperty("MXN")
    private double MXN;
    @JsonProperty("SGD")
    private double SGD;
    @JsonProperty("AUD")
    private double AUD;
    @JsonProperty("ILS")
    private double ILS;
    @JsonProperty("KRW")
    private double KRW;
    @JsonProperty("PLN")
    private double PLN;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
