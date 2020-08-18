package com.kodilla.walletmanager.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
public class CurrencyJson {
    @JsonProperty("rates")
    private RatesJson rates;
    @JsonProperty("base")
    private String base;
    @JsonProperty("date")
    private String date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
