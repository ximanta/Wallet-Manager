package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionServiceCRUD {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    private TransactionServiceCRUD(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.repository = transactionRepository;
        this.mapper = transactionMapper;
    }

    public TransactionDto create(final TransactionDto transactionDto){
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)){
            Transaction transaction = mapper.mapToEntity(transactionDto);
            Transaction fromDb = checkTransactionSave(transaction);
            return mapper.mapToDto(fromDb);
        }else {
            LOGGER.error("Incorrect object");
            throw new RuntimeException();
        }
    }

    public List<TransactionDto> getAll(){
        List<Transaction> transactions = repository.findAll();
        return mapper.mapToDtos(transactions);
    }

    public TransactionDto update(TransactionDto transactionDto){
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)){
            return updateMechanic(transactionDto);
        }else {
            throw new RuntimeException("Incorrect object");
        }
    }

    public boolean delete(final long transactionId){
        Optional<Transaction> optional = repository.findById(transactionId);
        if(optional.isPresent()){
            Transaction transaction = optional.get();
            repository.delete(transaction);
            return !repository.existsById(transactionId);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

    private TransactionDto updateMechanic(TransactionDto transactionDto){
        if (repository.existsById(transactionDto.getId())){
            Transaction transaction = mapper.mapToEntity(transactionDto);
            Transaction fromDb = checkTransactionSave(transaction);
            return mapper.mapToDto(fromDb);
        }else {
            LOGGER.error("Cannot find Transaction by id");
            throw new RuntimeException();
        }
    }

    private Transaction checkTransactionSave(Transaction transaction){
        try{
            Transaction formDb = repository.save(transaction);
            LOGGER.info("Transaction has been saved");
            return formDb;
        }catch (Exception e){
            LOGGER.error("Save error", e);
            throw new RuntimeException();
        }
    }

}
