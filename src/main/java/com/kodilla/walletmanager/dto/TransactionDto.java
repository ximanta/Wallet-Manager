package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Data;

import java.sql.Date;

@Data
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private double amount;
    private TransactionType type;
    private Date date;
    private CategoryDto categoryDto;

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        if (ToolsManager.isTheSameEnum(categoryDto.getType(),type)){
            this.categoryDto = categoryDto;
        }else {
            System.out.println("Not the came type TransactionType");
        }
    }
}
