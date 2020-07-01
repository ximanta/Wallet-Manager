package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Data;

@Data
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private double amount;

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }
}
