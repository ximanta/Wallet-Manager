package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.service.transaction.TransactionServiceDate;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionServiceCRUD transactionServiceCRUD;

    @Autowired
    TransactionServiceDate transactionServiceDate;

    //CRUD
    public TransactionDto create(final TransactionDto transactionDto){
        return transactionServiceCRUD.create(transactionDto);
    }

    public List<TransactionDto> getAll(){
        return transactionServiceCRUD.getAll();
    }

    public TransactionDto get(long transactionId){
        return transactionServiceCRUD.get(transactionId);
    }

    public TransactionDto update(TransactionDto transactionDto){
        return transactionServiceCRUD.update(transactionDto);
    }

    public boolean delete(final long transactionId){
        return transactionServiceCRUD.delete(transactionId);
    }

    //DATE
    public List<TransactionDto> findByDate(Date date){
        return transactionServiceDate.findByDate(date);
    }

    public List<TransactionDto> thisWeek(){
        return transactionServiceDate.thisWeek();
    }

    public List<TransactionDto> thisMonth(){
        return transactionServiceDate.thisMonth();
    }

    public List<TransactionDto> selectedMonth(int month, int year){
        return transactionServiceDate.selectedMonth(month,year);
    }

    public List<TransactionDto> betweenDate(Date fromDate, Date toDate){
        return transactionServiceDate.betweenDate(fromDate,toDate);
    }
}


