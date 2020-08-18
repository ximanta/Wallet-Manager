package com.kodilla.walletmanager.service.transaction;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.pojos.UserCertifying;
import com.kodilla.walletmanager.domain.pojos.ConvertCurrency;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.TransactionType;
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
    private final ExternalAPIsClient client;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    private TransactionServiceCRUD(TransactionRepository repository, TransactionMapper mapper, UserRepository userRepository, ExternalAPIsClient client) {
        this.transactionRepository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.client = client;
    }

    public TransactionDto create(final TransactionDto dto) {
        if (ToolsManager.isTransactionDtoCorrect(dto)) {
            Transaction transaction = mapper.mapToEntity(convertCurrency(dto));
            Transaction fromDb = checkTransactionSave(transaction);
            updateUserBalance(fromDb);
            return mapper.mapToDto(fromDb);
        }
        LOGGER.error("Incorrect object");
        throw new RuntimeException();
    }

    public List<TransactionDto> getAll(UserCertifying certifying) {
        Optional<User> user = userRepository.get(certifying.getLogin(), certifying.getPassword());
        if (user.isPresent()) {
            List<Transaction> transactions = transactionRepository.findAllByUser(user.get());
            LOGGER.info("Transaction has been loaded");
            return mapper.mapToDtos(transactions);
        } else {
            LOGGER.error("Cannot find user");
            throw new RuntimeException();
        }
    }

    public TransactionDto update(TransactionDto transactionDto) {
        if (ToolsManager.isTransactionDtoCorrect(transactionDto)) {
            return updateMechanic(transactionDto);
        }
        throw new RuntimeException("Incorrect object");
    }

    public boolean delete(final long id, UserCertifying certifying) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            if (checkDeleteUserLoginPassword(transaction.get(), certifying)) {
                transactionRepository.delete(transaction.get());
                if (!transactionRepository.existsById(id)) {
                    LOGGER.info("Transaction has been deleted");
                    reverseUserBalance(transaction.get());
                    return true;
                }
                LOGGER.info("Transaction hasn't been deleted");
                return false;
            }
            throw new RuntimeException("Wrong login or password");
        }
        throw new RuntimeException("Cannot find Transaction by id");
    }

    private TransactionDto updateMechanic(TransactionDto dto) {
        Optional<Transaction> optional = transactionRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Transaction transaction = mapper.mapToEntity(convertCurrency(dto));
            Transaction fromDb = checkTransactionSave(transaction);
            reverseUserBalance(optional.get());
            updateUserBalance(fromDb);
            return mapper.mapToDto(fromDb);
        }
        LOGGER.error("Cannot find Transaction by id");
        throw new RuntimeException();
    }

    private Transaction checkTransactionSave(Transaction transaction) {
        try {
            Transaction formDb = transactionRepository.save(transaction);
            LOGGER.info("Transaction has been saved");
            return formDb;
        } catch (Exception e) {
            LOGGER.error("Save error", e);
            throw new RuntimeException();
        }
    }

    private TransactionDto convertCurrency(TransactionDto dto) {
        if (!dto.getUserDto().getCurrencyType().equals(dto.getCurrencyType())) {
            ConvertCurrency currency = new ConvertCurrency(
                    dto.getCurrencyType().toString(),
                    dto.getUserDto().getCurrencyType().toString(),
                    dto.getAmount());
            double value = client.getConvertCurrency(currency);
            dto.setAmount(value);
        }
        return dto;
    }

    private void updateUserBalance(Transaction transaction) {
        Optional<User> optional = userRepository.findById(transaction.getUser().getId());
        if (optional.isPresent()) {
            User user = optional.get();
            double balance = user.getBalance();
            if (transaction.getCategory().getType().equals(TransactionType.EXPENSES)) {
                user.setBalance(balance - transaction.getAmount());
            } else {
                user.setBalance(balance + transaction.getAmount());
            }
            userRepository.save(user);
        }
    }

    private void reverseUserBalance(Transaction transaction) {
        Optional<User> optional = userRepository.findById(transaction.getUser().getId());
        if (optional.isPresent()) {
            User user = optional.get();
            double balance = user.getBalance();
            if (transaction.getCategory().getType().equals(TransactionType.EXPENSES)) {
                user.setBalance(balance + transaction.getAmount());
            } else {
                user.setBalance(balance - transaction.getAmount());
            }
            userRepository.save(user);
        }
    }

    private boolean checkDeleteUserLoginPassword(Transaction transaction, UserCertifying certifying) {
        User user = transaction.getUser();
        boolean isPassword = user.getPassword().equals(certifying.getPassword());
        boolean isLogin = user.getLogin().equals(certifying.getLogin());
        return isLogin && isPassword;
    }

}
