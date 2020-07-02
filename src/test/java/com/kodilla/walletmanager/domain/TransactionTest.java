package com.kodilla.walletmanager.domain;


import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    public TransactionRepository transactionRepository;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Transaction transaction = new Transaction();
        transaction.setTitle("Test");
        transaction.setDescription("Test description");
        transaction.setAmount(50);
        transaction.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction);

        //When
        Transaction fromDb = transactionRepository.getOne(transaction.getId());
        LocalDate localDate = LocalDate.now();

        //Then
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test description",fromDb.getDescription());
        assertEquals(50,fromDb.getAmount(),0);
        assertEquals(TransactionType.EXPENSES,fromDb.getType());
        assertEquals(localDate.getDayOfMonth(),fromDb.getDate().toLocalDate().getDayOfMonth());
        assertEquals(localDate.getMonthValue(),fromDb.getDate().toLocalDate().getMonthValue());
        assertEquals(localDate.getYear(),fromDb.getDate().toLocalDate().getYear());

        transactionRepository.delete(fromDb);
        assertFalse(transactionRepository.existsById(transaction.getId()));
    }
}
