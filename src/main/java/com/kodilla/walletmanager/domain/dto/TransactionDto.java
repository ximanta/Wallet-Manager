package com.kodilla.walletmanager.domain.dto;

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
    private double amount;
    private TransactionType type;
    private Date date;
    private CategoryDto categoryDto;
    private UserDto userDto;

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

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", categoryDto=" + categoryDto +
                ", user=" + userDto +
                '}';
    }
}
