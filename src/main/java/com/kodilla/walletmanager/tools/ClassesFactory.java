package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.builders.TransactionBuilder;
import com.kodilla.walletmanager.domain.builders.TransactionDtoBuilder;
import com.kodilla.walletmanager.domain.builders.UserBuilder;
import com.kodilla.walletmanager.domain.builders.UserDtoBuilder;
import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public final class ClassesFactory {

    public Transaction transaction(){
        return new TransactionBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.REVENUES)
                .currencyType(CurrencyType.USD)
                .amount(50)
                .category(category())
                .user(user())
                .build();
    }

    public TransactionDto transactionDto(){
        return new TransactionDtoBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.REVENUES)
                .currencyType(CurrencyType.USD)
                .amount(50)
                .category(categoryDto())
                .user(userDto()).build();
    }

    public Category category(){
        Category category = new Category();
        category.setName("Test");
        category.setType(TransactionType.REVENUES);
        return category;
    }

    public CategoryDto categoryDto(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test");
        categoryDto.setType(TransactionType.REVENUES);
        return categoryDto;
    }

    public User user(){
        return new UserBuilder()
                .login("Test")
                .password("Password")
                .emile("test@email.com")
                .birthDate(Date.valueOf("2000-02-20"))
                .active(true)
                .currencyType(CurrencyType.USD)
                .balance(-150).build();
    }

    public UserDto userDto(){
        return new UserDtoBuilder()
                .login("Test")
                .password("Password")
                .emile("test@email.com")
                .birthDate(Date.valueOf("2000-02-20"))
                .active(true)
                .currencyType(CurrencyType.USD)
                .balance(-150).build();
    }
}
