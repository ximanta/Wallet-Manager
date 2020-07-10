package com.kodilla.walletmanager.dto;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private TransactionType type;
    private Set<TransactionDto> transactionDtos = new HashSet<>();

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
