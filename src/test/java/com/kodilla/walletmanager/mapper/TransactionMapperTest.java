package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMapperTest {
    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void mapToEntity() {
        //Given
        TransactionDto transactionDto = factory.makeTransactionDto(ClassesFactory.COMPLETE);

        //When
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);

        //Then
        assertNull(transaction.getId());
        assertEquals("Test",transaction.getTitle());
        assertEquals("Test Description",transaction.getDescription());
        assertEquals(50,transaction.getAmount(),0);
        assertEquals(TransactionType.REVENUES, transaction.getType());
        assertEquals(createdCategory().getName(),transaction.getCategory().getName());
        assertEquals(createdCategory().getType(),transaction.getCategory().getType());
    }

    @Test
    public void mapToDto() {
        //Given
        Transaction transaction = factory.makeTransaction(ClassesFactory.COMPLETE);

        //When
        TransactionDto transactionDto = transactionMapper.mapToDto(transaction);

        //Then
        assertNull(transactionDto.getId());
        assertEquals("Test",transactionDto.getTitle());
        assertEquals("Test Description",transactionDto.getDescription());
        assertEquals(50,transactionDto.getAmount(),0);
        assertEquals(TransactionType.REVENUES, transactionDto.getType());
        assertEquals(createdCategory().getName(),transactionDto.getCategoryDto().getName());
        assertEquals(createdCategory().getType(),transactionDto.getCategoryDto().getType());
    }

    @Test
    public void mapToDtos() {
        List<Transaction> transactions = factory.makeTransactionList(ClassesFactory.FIVEOBJECT);

        //When
        List<TransactionDto> transactionDtos = transactionMapper.mapToDtos(transactions);
        TransactionDto transactionDto = transactionDtos.get(0);

        //Then
        assertNull(transactionDto.getId());
        assertNotNull(transactionDto.getCategoryDto());
        assertEquals("Test",transactionDto.getTitle());
        assertEquals("Test Description",transactionDto.getDescription());
        assertEquals(50,transactionDto.getAmount(),0);
        assertEquals(5,transactionDtos.size());
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

    private Category createdCategory(){
        return factory.makeCategory(ClassesFactory.COMPLETE);
    }
}