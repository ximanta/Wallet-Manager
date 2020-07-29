package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class TransactionServiceDate {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    private TransactionServiceDate(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.repository = transactionRepository;
        this.mapper = transactionMapper;
    }

    public List<TransactionDto> thisWeek(){
        List<Transaction> transactions = repository.thisWeek();
        return mapper.mapToDtos(transactions);
    }

    public List<TransactionDto> thisMonth(){
        List<Transaction> transactions = repository.thisMonth();
        return mapper.mapToDtos(transactions);
    }

    public List<TransactionDto> betweenDate(String fromDate, String toDate){
        Date from = Date.valueOf(fromDate);
        Date to = Date.valueOf(toDate);
        List<Transaction> transactions = repository.betweenDate(from,to);
        return mapper.mapToDtos(transactions);
    }
}
