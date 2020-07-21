package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String emile;
    private double balance;

    public void setBalance(double balance) {
        this.balance = ToolsManager.tenthRoundDouble(balance);
    }
}
