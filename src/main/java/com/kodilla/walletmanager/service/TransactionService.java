package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.service.transaction.TransactionServiceDate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private TransactionServiceCRUD transactionServiceCRUD;
    private TransactionServiceDate transactionServiceDate;

    public TransactionService(TransactionServiceCRUD transactionServiceCRUD, TransactionServiceDate transactionServiceDate) {
        this.transactionServiceCRUD = transactionServiceCRUD;
        this.transactionServiceDate = transactionServiceDate;
    }

    //CRUD
    public TransactionDto create(final TransactionDto transactionDto){
        return transactionServiceCRUD.create(transactionDto);
    }

    public List<TransactionDto> getAll(){
        return transactionServiceCRUD.getAll();
    }

    public TransactionDto update(TransactionDto transactionDto){
        return transactionServiceCRUD.update(transactionDto);
    }

    public boolean delete(final long transactionId){
        return transactionServiceCRUD.delete(transactionId);
    }

    //DATE

    public List<TransactionDto> thisWeek(){
        return transactionServiceDate.thisWeek();
    }

    public List<TransactionDto> thisMonth(){
        return transactionServiceDate.thisMonth();
    }

    public List<TransactionDto> betweenDate(String fromDate, String toDate){
        return transactionServiceDate.betweenDate(fromDate,toDate);
    }
}


