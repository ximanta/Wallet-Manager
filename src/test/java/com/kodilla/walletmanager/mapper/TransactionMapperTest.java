package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMapperTest {
    @Autowired
    TransactionMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void mapToEntity() {
        //Given
        TransactionDto transactionDto = factory.transactionDto();
        transactionDto.setDate(Date.valueOf("2020-02-20"));

        //When
        Transaction transaction = mapper.mapToEntity(transactionDto);

        //Then
        assertNull(transaction.getId());
        assertEquals("Test",transaction.getTitle());
        assertEquals("Test Description",transaction.getDescription());
        assertEquals(Date.valueOf("2020-02-20"),transaction.getDate());
        assertEquals(TransactionType.REVENUES, transaction.getType());
        assertEquals(CurrencyType.USD,transactionDto.getCurrencyType());
        assertEquals(50,transaction.getAmount(),0);
        assertNotNull(transaction.getCategory());
        assertNotNull(transaction.getUser());
    }

    @Test
    public void mapToDto() {
        //Given
        Transaction transaction = factory.transaction();
        transaction.setDate(Date.valueOf("2020-02-20"));

        //When
        TransactionDto dto = mapper.mapToDto(transaction);

        //Then
        assertNull(dto.getId());
        assertEquals("Test",dto.getTitle());
        assertEquals("Test Description",dto.getDescription());
        assertEquals(Date.valueOf("2020-02-20"),dto.getDate());
        assertEquals(TransactionType.REVENUES, dto.getType());
        assertEquals(CurrencyType.USD,dto.getCurrencyType());
        assertEquals(50,dto.getAmount(),0);
        assertNotNull(dto.getCategoryDto());
        assertNotNull(dto.getUserDto());
    }

    @Test
    public void mapToDtos() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 3; i++){
            transactions.add(factory.transaction());
        }

        //When
        List<TransactionDto> transactionDtos = mapper.mapToDtos(transactions);
        TransactionDto dto = transactionDtos.get(0);

        //Then
        assertNull(dto.getId());
        assertEquals("Test",dto.getTitle());
        assertEquals("Test Description",dto.getDescription());
        assertEquals(TransactionType.REVENUES, dto.getType());
        assertEquals(CurrencyType.USD,dto.getCurrencyType());
        assertEquals(50,dto.getAmount(),0);
        assertNotNull(dto.getCategoryDto());
        assertNotNull(dto.getUserDto());
    }

    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<Transaction> transactions = new ArrayList<>();

        //When
        List<TransactionDto> transactionDtos = mapper.mapToDtos(transactions);

        //Then
        assertTrue(transactionDtos.isEmpty());
    }
}