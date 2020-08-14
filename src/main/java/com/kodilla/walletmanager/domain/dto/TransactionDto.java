package com.kodilla.walletmanager.domain.dto;

import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private CurrencyType currencyType;
    private double amount;
    private Date date;
    private CategoryDto categoryDto;
    private UserDto userDto;

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                ", date=" + date + '}';
    }
}
