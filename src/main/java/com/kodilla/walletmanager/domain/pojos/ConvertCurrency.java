package com.kodilla.walletmanager.domain.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ConvertCurrency {
    private String fromCurrency;
    private String toCurrency;
    private double amount;
}
