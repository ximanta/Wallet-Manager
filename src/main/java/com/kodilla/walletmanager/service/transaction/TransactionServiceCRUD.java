package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserLoginPassword;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionServiceCRUD {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    private TransactionServiceCRUD(TransactionRepository repository, TransactionMapper mapper, UserRepository userRepository) {
        this.transactionRepository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public TransactionDto create(final TransactionDto dto){
        if (ToolsManager.isTransactionDtoCorrect(dto)){
            Transaction transaction = mapper.mapToEntity(dto);
            Transaction fromDb = checkTransactionSave(transaction);
            return mapper.mapToDto(fromDb);
        }else {
            LOGGER.error("Incorrect object");
            throw new RuntimeException();
        }
    }

    public List<TransactionDto> getAll(UserLoginPassword loginPassword){
        Optional<User> user = userRepository.get(loginPassword.getLogin(),loginPassword.getPassword());
        if (user.isPresent()){
            List<Transaction> transactions = transactionRepository.findAllByUser(user.get());
            LOGGER.info("Transaction has been loaded");
            return mapper.mapToDtos(transactions);
        }else {
            LOGGER.error("Cannot find user");
            throw new RuntimeException();
        }
    }

    public TransactionDto update(TransactionDto transactionDto){
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)){
            return updateMechanic(transactionDto);
        }else {
            throw new RuntimeException("Incorrect object");
        }
    }

    public boolean delete(final long id){
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()){
            transactionRepository.delete(transaction.get());
            LOGGER.info("Transaction has been deleted");
            return !transactionRepository.existsById(id);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

    private TransactionDto updateMechanic(TransactionDto dto){
        if (transactionRepository.existsById(dto.getId())){
            Transaction transaction = mapper.mapToEntity(dto);
            Transaction fromDb = checkTransactionSave(transaction);
            return mapper.mapToDto(fromDb);
        }else {
            LOGGER.error("Cannot find Transaction by id");
            throw new RuntimeException();
        }
    }

    private Transaction checkTransactionSave(Transaction transaction){
        try{
            Transaction formDb = transactionRepository.save(transaction);
            LOGGER.info("Transaction has been saved");
            return formDb;
        }catch (Exception e){
            LOGGER.error("Save error", e);
            throw new RuntimeException();
        }
    }

}
