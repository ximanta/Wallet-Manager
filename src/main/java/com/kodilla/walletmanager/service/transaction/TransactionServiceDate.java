package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserCertifying;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class TransactionServiceDate {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    public TransactionServiceDate(TransactionRepository repository, TransactionMapper transactionMapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = transactionMapper;
        this.userRepository = userRepository;
    }


    public List<TransactionDto> thisWeek(UserCertifying certifying){
        Optional<User> user = getOptional(certifying);
        if (user.isPresent()){
            List<Transaction> transactions = repository.thisWeek(user.get());
            LOGGER.info("This week transactions has been loaded");
            return mapper.mapToDtos(transactions);
        }else {
            LOGGER.error("Cannot load Transactions");
            throw new RuntimeException();
        }

    }

    public List<TransactionDto> thisMonth(UserCertifying certifying){
        Optional<User> user = getOptional(certifying);
        if (user.isPresent()){
            List<Transaction> transactions = repository.thisMonth(user.get());
            LOGGER.info("This Month transactions has been loaded");
            return mapper.mapToDtos(transactions);
        }else {
            LOGGER.error("Cannot load Transactions");
            throw new RuntimeException();
        }
    }

    public List<TransactionDto> betweenDate(String fromDate, String toDate, UserCertifying certifying){
        Optional<User> user = getOptional(certifying);
        if (user.isPresent()){
            Date from = Date.valueOf(fromDate);
            Date to = Date.valueOf(toDate);
            List<Transaction> transactions = repository.betweenDate(from,to,user.get());
            LOGGER.info("Transactions between date has been loaded");
            return mapper.mapToDtos(transactions);
        }else {
            LOGGER.error("Cannot load Transactions");
            throw new RuntimeException();
        }

    }

    private Optional<User> getOptional(UserCertifying certifying){
        return userRepository.get(certifying.getLogin(),certifying.getPassword());
    }
}
