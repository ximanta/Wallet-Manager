package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMapperTest {
    @Autowired
    TransactionMapper transactionMapper;

    @Test
    public void mapToEntity() {
        //Given
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTitle("Test");
        transactionDto.setDescription("Test description");
        transactionDto.setAmount(100);

        //When
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);

        //Then
        assertNull(transaction.getId());
        assertEquals("Test",transaction.getTitle());
        assertEquals("Test description",transaction.getDescription());
        assertEquals(100,transaction.getAmount());
    }

    @Test
    public void mapToDto() {
        //Given
        Transaction transaction = new Transaction();
        transaction.setTitle("Test");
        transaction.setDescription("Test description");
        transaction.setAmount(100);

        //When
        TransactionDto transactionDto = transactionMapper.mapToDto(transaction);

        //Then
        assertNull(transactionDto.getId());
        assertEquals("Test",transactionDto.getTitle());
        assertEquals("Test description",transactionDto.getDescription());
        assertEquals(100,transactionDto.getAmount());
    }

    @Test
    public void mapToDtos() {
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test1");
        transaction1.setDescription("Test1 description");
        transaction1.setAmount(110);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test2");
        transaction2.setDescription("Test2 description");
        transaction2.setAmount(120);

        transactions.add(transaction1);
        transactions.add(transaction2);

        //When
        List<TransactionDto> transactionDtos = transactionMapper.mapToDtos(transactions);

        TransactionDto transactionDto1 = transactionDtos.get(0);
        TransactionDto transactionDto2 = transactionDtos.get(1);

        //Then
        assertNull(transactionDto1.getId());
        assertEquals("Test1",transactionDto1.getTitle());
        assertEquals("Test1 description",transactionDto1.getDescription());
        assertEquals(110,transactionDto1.getAmount());

        assertNull(transactionDto2.getId());
        assertEquals("Test2",transactionDto2.getTitle());
        assertEquals("Test2 description",transactionDto2.getDescription());
        assertEquals(120,transactionDto2.getAmount());
    }

    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<Transaction> transactions = new ArrayList<>();

        //When
        List<TransactionDto> transactionDtos = transactionMapper.mapToDtos(transactions);

        //Then
        assertTrue(transactionDtos.isEmpty());
    }
}