package com.kodilla.walletmanager.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Bitcoin {
    @JsonProperty("cad")
    private double CAD;
    @JsonProperty("hkd")
    private double HKD;
    @JsonProperty("php")
    private double PHP;
    @JsonProperty("dkk")
    private double DKK;
    @JsonProperty("huf")
    private double HUF;
    @JsonProperty("czk")
    private double CZK;
    @JsonProperty("gbp")
    private double GBP;
    @JsonProperty("sek")
    private double SEK;
    @JsonProperty("idr")
    private double IDR;
    @JsonProperty("inr")
    private double INR;
    @JsonProperty("brl")
    private double BRL;
    @JsonProperty("rub")
    private double RUB;
    @JsonProperty("jpy")
    private double JPY;
    @JsonProperty("thb")
    private double THB;
    @JsonProperty("chf")
    private double CHF;
    @JsonProperty("eur")
    private double EUR;
    @JsonProperty("myr")
    private double MYR;
    @JsonProperty("bgn")
    private double BGN;
    @JsonProperty("try")
    private double TRY;
    @JsonProperty("cny")
    private double CNY;
    @JsonProperty("nok")
    private double NOK;
    @JsonProperty("nzd")
    private double NZD;
    @JsonProperty("zar")
    private double ZAR;
    @JsonProperty("usd")
    private double USD;
    @JsonProperty("mxn")
    private double MXN;
    @JsonProperty("sgd")
    private double SGD;
    @JsonProperty("aud")
    private double AUD;
    @JsonProperty("krw")
    private double KRW;
    @JsonProperty("pln")
    private double PLN;
}
