package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private TransactionType type;
}
