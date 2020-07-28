package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.service.transaction.TransactionServiceDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionServiceCRUD transactionServiceCRUD;

    @Autowired
    private TransactionServiceDate transactionServiceDate;

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
    public List<TransactionDto> findByDate(String date){
        return transactionServiceDate.findByDate(date);
    }

    public List<TransactionDto> thisWeek(){
        return transactionServiceDate.thisWeek();
    }

    public List<TransactionDto> thisMonth(){
        return transactionServiceDate.thisMonth();
    }

    public List<TransactionDto> selectedMonth(String year_month){
        return transactionServiceDate.selectedMonth(year_month);
    }

    public List<TransactionDto> betweenDate(String fromDate, String toDate){
        return transactionServiceDate.betweenDate(fromDate,toDate);
    }
}


