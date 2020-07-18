package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionServiceCRUD {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionDto create(final TransactionDto transactionDto){
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)){
            Transaction transaction = transactionMapper.mapToEntity(transactionDto);
            Transaction fromDb = transactionRepository.save(transaction);
            return transactionMapper.mapToDto(fromDb);
        }else {
            throw new RuntimeException("Incorrect object");
        }
    }

    public List<TransactionDto> getAll(){
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.mapToDtos(transactions);
    }

    public TransactionDto get(long transactionId){
        Optional<Transaction> optional = transactionRepository.findById(transactionId);
        if (optional.isPresent()){
            Transaction transaction = optional.get();
            return transactionMapper.mapToDto(transaction);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

    public TransactionDto update(TransactionDto transactionDto){
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)){
            return updateMechanic(transactionDto);
        }else {
            throw new RuntimeException("Incorrect object");
        }
    }

    public boolean delete(final long transactionId){
        Optional<Transaction> optional = transactionRepository.findById(transactionId);
        if(optional.isPresent()){
            Transaction transaction = optional.get();
            transactionRepository.delete(transaction);
            return !transactionRepository.existsById(transactionId);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

    private TransactionDto updateMechanic(TransactionDto transactionDto){
        Optional<Transaction> optional = transactionRepository.findById(transactionDto.getId());
        if (optional.isPresent()){
            Category category = categoryMapper.mapToEntity(transactionDto.getCategoryDto());
            Transaction transaction = optional.get();
            transaction.setTitle(transactionDto.getTitle());
            transaction.setDescription(transactionDto.getDescription());
            transaction.setType(transactionDto.getType());
            transaction.setDate(transactionDto.getDate());
            transaction.setAmount(transactionDto.getAmount());
            transaction.setCategory(category);
            transactionRepository.save(transaction);
            return transactionMapper.mapToDto(transaction);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

}
