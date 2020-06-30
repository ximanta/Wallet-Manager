package com.kodilla.walletmanager.dto;

import lombok.Data;

@Data
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private long amount;
}
