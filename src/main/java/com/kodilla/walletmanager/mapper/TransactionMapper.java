package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    @Autowired
    CategoryMapper categoryMapper;

    public Transaction mapToEntity(TransactionDto transactionDto){
        Category category = categoryMapper.mapToEntity(transactionDto.getCategoryDto());
        return new Transaction.TransactionBuilder()
                .id(transactionDto.getId())
                .title(transactionDto.getTitle())
                .description(transactionDto.getDescription())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .date(transactionDto.getDate())
                .category(category).build();
    }

    public TransactionDto mapToDto(Transaction transaction){
        CategoryDto categoryDto = categoryMapper.mapToDto(transaction.getCategory());
        return new TransactionDto.TransactionDtoBuilder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .date(transaction.getDate())
                .category(categoryDto).build();
    }

    public List<TransactionDto> mapToDtos(List<Transaction> transactions){
        return transactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
