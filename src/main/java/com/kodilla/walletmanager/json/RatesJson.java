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
    private Double cAD;
    @JsonProperty("HKD")
    private Double hKD;
    @JsonProperty("ISK")
    private Double iSK;
    @JsonProperty("PHP")
    private Double pHP;
    @JsonProperty("DKK")
    private Double dKK;
    @JsonProperty("HUF")
    private Double hUF;
    @JsonProperty("CZK")
    private Double cZK;
    @JsonProperty("GBP")
    private Double gBP;
    @JsonProperty("RON")
    private Double rON;
    @JsonProperty("SEK")
    private Double sEK;
    @JsonProperty("IDR")
    private Double iDR;
    @JsonProperty("INR")
    private Double iNR;
    @JsonProperty("BRL")
    private Double bRL;
    @JsonProperty("RUB")
    private Double rUB;
    @JsonProperty("HRK")
    private Double hRK;
    @JsonProperty("JPY")
    private Double jPY;
    @JsonProperty("THB")
    private Double tHB;
    @JsonProperty("CHF")
    private Double cHF;
    @JsonProperty("EUR")
    private Double eUR;
    @JsonProperty("MYR")
    private Double mYR;
    @JsonProperty("BGN")
    private Double bGN;
    @JsonProperty("TRY")
    private Double tRY;
    @JsonProperty("CNY")
    private Double cNY;
    @JsonProperty("NOK")
    private Double nOK;
    @JsonProperty("NZD")
    private Double nZD;
    @JsonProperty("ZAR")
    private Double zAR;
    @JsonProperty("USD")
    private Double uSD;
    @JsonProperty("MXN")
    private Double mXN;
    @JsonProperty("SGD")
    private Double sGD;
    @JsonProperty("AUD")
    private Double aUD;
    @JsonProperty("ILS")
    private Double iLS;
    @JsonProperty("KRW")
    private Double kRW;
    @JsonProperty("PLN")
    private Double pLN;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
