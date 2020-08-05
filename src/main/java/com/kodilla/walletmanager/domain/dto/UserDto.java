package com.kodilla.walletmanager.domain.dto;

import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String emile;
    private Date birthDate;
    private boolean active;
    private CurrencyType currencyType;
    private double balance;

    public void setBalance(double balance) {
        this.balance = ToolsManager.tenthRoundDouble(balance);
    }
}
