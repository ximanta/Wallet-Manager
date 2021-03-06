package com.kodilla.walletmanager.domain;


import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {
    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    ClassesFactory factory;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Transaction transaction = createTransaction();

        //When
        Transaction fromDb = transactionRepository.getOne(transaction.getId());
        LocalDate today = LocalDate.now();
        transactionRepository.delete(fromDb);
        categoryRepository.delete(fromDb.getCategory());
        userRepository.delete(fromDb.getUser());
        String toString = "Transaction{" +
                "id=" + fromDb.getId() +
                ", date=" + fromDb.getDate() +
                ", title='" + fromDb.getTitle() + '\'' +
                ", description='" + fromDb.getDescription() + '\'' +
                ", currencyType=" + fromDb.getCurrencyType() +
                ", amount=" + fromDb.getAmount() +
                ", category=" + fromDb.getCategory() +
                ", user=" + fromDb.getUser() +
                '}';

        //Then
        assertNotNull(fromDb.getId());
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test Description",fromDb.getDescription());
        assertEquals(CurrencyType.USD,fromDb.getCurrencyType());
        assertEquals(50,fromDb.getAmount(),0);
        assertEquals(today.getDayOfMonth(),fromDb.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),fromDb.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),fromDb.getDate().toLocalDate().getYear());

        assertNotNull(fromDb.getCategory().getId());
        assertEquals(transaction.getCategory().getName(),fromDb.getCategory().getName());
        assertEquals(transaction.getCategory().getType(),fromDb.getCategory().getType());
        assertEquals(toString,fromDb.toString());

        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
        assertFalse(userRepository.existsById(fromDb.getUser().getId()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void createIncompleteRecordTest(){
        Transaction transaction = new Transaction();
        Transaction formDb = transactionRepository.save(transaction);
        assertTrue(transactionRepository.existsById(formDb.getId()));
    }


    private Transaction createTransaction(){
        Transaction transaction = factory.transaction();
        transaction.setCategory(createdCategory());
        transaction.setUser(createUser());
        return transactionRepository.save(transaction);
    }

    private Category createdCategory(){
        return categoryRepository.save(factory.category());
    }

    private User createUser(){
        Optional<User> optional = userRepository.getByLogin(factory.user().getLogin());
        return optional.orElseGet(() -> userRepository.save(factory.user()));
    }
}
