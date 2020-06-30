package com.kodilla.walletmanager.domain;


import com.kodilla.walletmanager.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    public TransactionRepository transactionRepository;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Transaction transaction = new Transaction("Test","Test description",50);

        transactionRepository.save(transaction);

        //When
        Transaction fromDb = transactionRepository.getOne(transaction.getId());

        //Then
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test description",fromDb.getDescription());
        assertEquals(50,fromDb.getAmount());

        transactionRepository.delete(fromDb);
        assertFalse(transactionRepository.existsById(transaction.getId()));
    }

}
