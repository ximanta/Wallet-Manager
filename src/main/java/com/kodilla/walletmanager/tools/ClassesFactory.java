package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
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
        return new Transaction.TransactionBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.REVENUES)
                .amount(50)
                .category(category())
                .build();
    }

    public TransactionDto transactionDto(){
        return new TransactionDto.TransactionDtoBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.REVENUES)
                .amount(50)
                .category(categoryDto())
                .build();
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
        User user = new User();
        user.setBalance(-150);
        user.setEmile("test@email.com");
        return user;
    }

    public UserDto userDto(){
        UserDto userDto = new UserDto();
        userDto.setBalance(-150);
        userDto.setEmile("test@email.com");
        return userDto;
    }
}
