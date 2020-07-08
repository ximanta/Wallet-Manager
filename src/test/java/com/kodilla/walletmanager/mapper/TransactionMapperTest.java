package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
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

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    public void mapToEntity() {
        //Given
        CategoryDto categoryDto = categoryMapper.mapToDto(createdCategory());
        TransactionDto transactionDto = new TransactionDto.TransactionDtoBuilder()
                .title("Test")
                .description("Test description")
                .amount(100)
                .type(TransactionType.REVENUES)
                .category(categoryDto).build();

        //When
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);

        //Then
        assertNull(transaction.getId());
        assertEquals("Test",transaction.getTitle());
        assertEquals("Test description",transaction.getDescription());
        assertEquals(100,transaction.getAmount(),0);
        assertEquals(TransactionType.REVENUES, transaction.getType());
        assertEquals(categoryDto.getName(),transaction.getCategory().getName());
        assertEquals(categoryDto.getType(),transaction.getCategory().getType());
    }

    @Test
    public void mapToDto() {
        //Given
        Transaction transaction = new Transaction.TransactionBuilder()
                .title("Test")
                .description("Test description")
                .amount(100)
                .type(TransactionType.REVENUES)
                .category(createdCategory()).build();

        //When
        TransactionDto transactionDto = transactionMapper.mapToDto(transaction);

        //Then
        assertNull(transactionDto.getId());
        assertEquals("Test",transactionDto.getTitle());
        assertEquals("Test description",transactionDto.getDescription());
        assertEquals(100,transactionDto.getAmount(),0);
        assertEquals(TransactionType.REVENUES, transactionDto.getType());
        assertEquals(createdCategory().getName(),transactionDto.getCategoryDto().getName());
        assertEquals(createdCategory().getType(),transactionDto.getCategoryDto().getType());
    }

    @Test
    public void mapToDtos() {
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction.TransactionBuilder()
                .title("Test 1")
                .description("Test 1 description")
                .amount(110)
                .type(TransactionType.REVENUES)
                .category(createdCategory()).build();

        Transaction transaction2 = new Transaction.TransactionBuilder()
                .title("Test 2")
                .description("Test 2 description")
                .amount(120)
                .type(TransactionType.REVENUES)
                .category(createdCategory()).build();

        transactions.add(transaction1);
        transactions.add(transaction2);

        //When
        List<TransactionDto> transactionDtos = transactionMapper.mapToDtos(transactions);

        TransactionDto transactionDto1 = transactionDtos.get(0);
        TransactionDto transactionDto2 = transactionDtos.get(1);

        //Then
        assertNull(transactionDto1.getId());
        assertNotNull(transactionDto1.getCategoryDto());
        assertEquals("Test 1",transactionDto1.getTitle());
        assertEquals("Test 1 description",transactionDto1.getDescription());
        assertEquals(110,transactionDto1.getAmount(),0);

        assertNull(transactionDto2.getId());
        assertNotNull(transactionDto2.getCategoryDto());
        assertEquals("Test 2",transactionDto2.getTitle());
        assertEquals("Test 2 description",transactionDto2.getDescription());
        assertEquals(120,transactionDto2.getAmount(),0);
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
        Category category = new Category();
        category.setName("Test");
        category.setType(TransactionType.REVENUES);

        return category;
    }
}