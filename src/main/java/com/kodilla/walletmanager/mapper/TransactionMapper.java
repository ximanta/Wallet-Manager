package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.builders.TransactionBuilder;
import com.kodilla.walletmanager.domain.builders.TransactionDtoBuilder;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    CategoryMapper categoryMapper;
    UserMapper userMapper;

    private TransactionMapper(CategoryMapper categoryMapper, UserMapper userMapper) {
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

    public Transaction mapToEntity(TransactionDto transactionDto){
        Category category = categoryMapper.mapToEntity(transactionDto.getCategoryDto());
        User user = userMapper.mapToEntity(transactionDto.getUserDto());
        return new TransactionBuilder()
                .id(transactionDto.getId())
                .title(transactionDto.getTitle())
                .description(transactionDto.getDescription())
                .currencyType(transactionDto.getCurrencyType())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .date(transactionDto.getDate())
                .category(category)
                .user(user).build();
    }

    public TransactionDto mapToDto(Transaction transaction){
        CategoryDto categoryDto = categoryMapper.mapToDto(transaction.getCategory());
        UserDto userDto = userMapper.mapToDto(transaction.getUser());
        return new TransactionDtoBuilder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .description(transaction.getDescription())
                .currencyType(transaction.getCurrencyType())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .date(transaction.getDate())
                .category(categoryDto)
                .user(userDto).build();
    }

    public List<TransactionDto> mapToDtos(List<Transaction> transactions){
        return transactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
