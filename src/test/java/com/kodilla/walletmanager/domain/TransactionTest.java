package com.kodilla.walletmanager.domain;


import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {
    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Transaction transaction = transactionRepository.save(createTransaction());

        //When
        Transaction fromDb = transactionRepository.getOne(transaction.getId());
        LocalDate today = LocalDate.now();
        transactionRepository.delete(fromDb);
        categoryRepository.delete(fromDb.getCategory());

        //Then
        assertNotNull(fromDb.getId());
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test description",fromDb.getDescription());
        assertEquals(50,fromDb.getAmount(),0);
        assertEquals(TransactionType.REVENUES,fromDb.getType());
        assertEquals(today.getDayOfMonth(),fromDb.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),fromDb.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),fromDb.getDate().toLocalDate().getYear());

        assertNotNull(fromDb.getCategory().getId());
        assertEquals(createdCategory().getName(),fromDb.getCategory().getName());
        assertEquals(createdCategory().getType(),fromDb.getCategory().getType());

        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
    }

    @Test
    public void createUncompletedRecordTest(){
        //Given
        Transaction transaction = new Transaction();
        transaction.setTitle("Test");
        transaction.setType(TransactionType.REVENUES);
        transaction.setDate(Date.valueOf("2018-06-25"));
        transaction.setCategory(createdCategory());

        transactionRepository.save(transaction);

        //When
        Transaction fromDb = transactionRepository.getOne(transaction.getId());
        transactionRepository.delete(fromDb);
        categoryRepository.delete(fromDb.getCategory());

        //Then
        assertEquals("Test",fromDb.getTitle());
        assertNull(transaction.getDescription());
        assertEquals(0,fromDb.getAmount(),0);
        assertEquals(TransactionType.REVENUES,fromDb.getType());
        assertEquals(25,fromDb.getDate().toLocalDate().getDayOfMonth());
        assertEquals(6,fromDb.getDate().toLocalDate().getMonthValue());
        assertEquals(2018,fromDb.getDate().toLocalDate().getYear());

        assertNotNull(fromDb.getCategory().getId());
        assertEquals(createdCategory().getName(),fromDb.getCategory().getName());
        assertEquals(createdCategory().getType(),fromDb.getCategory().getType());

        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
    }

    private Transaction createTransaction(){
        Transaction transaction = new Transaction();
        transaction.setTitle("Test");
        transaction.setDescription("Test description");
        transaction.setAmount(50);
        transaction.setType(TransactionType.REVENUES);
        transaction.setCategory(createdCategory());

        return transaction;
    }

    private Category createdCategory(){
        Category category = new Category();
        category.setName("Test");
        category.setType(TransactionType.REVENUES);

        return category;
    }
}
