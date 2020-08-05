package com.kodilla.walletmanager.domain.builders;

import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.domain.enums.TransactionType;

import java.sql.Date;

public class TransactionDtoBuilder {
    private Long id;
    private Date date = new Date(new java.util.Date().getTime());
    private TransactionType transactionType;
    private String title;
    private String description;
    private CurrencyType currencyType;
    private double amount;
    private CategoryDto category;
    private UserDto userDto;

    public TransactionDtoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TransactionDtoBuilder date(Date date) {
        this.date = date;
        return this;
    }

    public TransactionDtoBuilder type(TransactionType type) {
        this.transactionType = type;
        return this;
    }

    public TransactionDtoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TransactionDtoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TransactionDtoBuilder currencyType(CurrencyType type) {
        this.currencyType = type;
        return this;
    }

    public TransactionDtoBuilder amount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionDtoBuilder category(CategoryDto categoryDto) {
        this.category = categoryDto;
        return this;
    }

    public TransactionDtoBuilder user(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public TransactionDto build() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(id);
        transactionDto.setDate(date);
        transactionDto.setType(transactionType);
        transactionDto.setTitle(title);
        transactionDto.setDescription(description);
        transactionDto.setCurrencyType(currencyType);
        transactionDto.setAmount(amount);
        transactionDto.setCategoryDto(category);
        transactionDto.setUserDto(userDto);

        return transactionDto;
    }

}