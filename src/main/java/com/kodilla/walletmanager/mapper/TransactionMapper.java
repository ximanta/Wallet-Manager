package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    public Transaction mapToEntity(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setTitle(transactionDto.getTitle());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());

        return transaction;
    }

    public TransactionDto mapToDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTitle(transaction.getTitle());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setType(transaction.getType());

        return transactionDto;
    }

    public List<TransactionDto> mapToDtos(List<Transaction> transactions){
        return transactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


}
