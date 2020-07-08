package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private TransactionType type;
    private Set<TransactionDto> transactionDtos;
}
