package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.pojos.UserCertifying;
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

    public TransactionDto create(final TransactionDto transactionDto) {
        return transactionServiceCRUD.create(transactionDto);
    }

    public List<TransactionDto> getAll(UserCertifying certifying) {
        return transactionServiceCRUD.getAll(certifying);
    }

    public TransactionDto update(TransactionDto transactionDto) {
        return transactionServiceCRUD.update(transactionDto);
    }

    public boolean delete(final long transactionId, UserCertifying certifying) {
        return transactionServiceCRUD.delete(transactionId, certifying);
    }

    public List<TransactionDto> thisWeek(UserCertifying dto) {
        return transactionServiceDate.thisWeek(dto);
    }

    public List<TransactionDto> thisMonth(UserCertifying dto) {
        return transactionServiceDate.thisMonth(dto);
    }

    public List<TransactionDto> betweenDate(String fromDate, String toDate, UserCertifying certifying) {
        return transactionServiceDate.betweenDate(fromDate, toDate, certifying);
    }
}


