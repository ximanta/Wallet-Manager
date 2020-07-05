package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionServiceDate {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public List<TransactionDto> findByDate(Date date){
        List<Transaction> transactions = transactionRepository.findByDate(date);
        return transactionMapper.mapToDtos(transactions);
    }

    public List<TransactionDto> thisWeek(){
        List<Transaction> transactions = transactionRepository.thisWeek();
        return transactionMapper.mapToDtos(transactions);
    }

    public List<TransactionDto> thisMonth(){
        List<Transaction> transactions = transactionRepository.thisMonth();
        return transactionMapper.mapToDtos(transactions);
    }

    public List<TransactionDto> selectedMonth(int month, int year){
        if (ToolsManager.isMonthCorrect(month,year)){
            List<Transaction> transactions = transactionRepository.selectedMonth(month,year);
            return transactionMapper.mapToDtos(transactions);
        }else {
            return new ArrayList<>();
        }
    }

    public List<TransactionDto> betweenDate(Date fromDate, Date toDate){
        List<Transaction> transactions = transactionRepository.betweenDate(fromDate,toDate);
        return transactionMapper.mapToDtos(transactions);
    }
}
