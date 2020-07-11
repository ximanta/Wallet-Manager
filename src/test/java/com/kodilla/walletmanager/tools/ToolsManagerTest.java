package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ToolsManagerTest {

    @Test
    public void positiveTenthRoundDouble() {
        //When
        double negative = ToolsManager.positiveTenthRoundDouble(-20);
        double correct = ToolsManager.positiveTenthRoundDouble(25.25);
        double thousandValue = ToolsManager.positiveTenthRoundDouble(0.0049);
        double round = ToolsManager.positiveTenthRoundDouble(1.999);

        //Then
        assertEquals(0,negative,0);
        assertEquals(25.25,correct,0);
        assertEquals(0,thousandValue,0);
        assertEquals(2,round,0);
    }

    @Test
    public void isTransactionDtoCorrect() {
        //Given
        TransactionDto transactionDto = new TransactionDto();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setType(TransactionType.EXPENSES);
        categoryDto.setName("Test");

        //When Then
        boolean isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertFalse(isCorrect);

        transactionDto.setDate(Date.valueOf("2018-06-25"));
        isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertFalse(isCorrect);

        transactionDto.setType(TransactionType.EXPENSES);
        isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertFalse(isCorrect);

        transactionDto.setCategoryDto(categoryDto);
        isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertFalse(isCorrect);

        transactionDto.setTitle("");
        isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertFalse(isCorrect);

        transactionDto.setTitle("TEst");
        isCorrect = ToolsManager.isTransactionDtoCorrect(transactionDto);
        assertTrue(isCorrect);
    }

    @Test
    public void isMonthCorrect() {
        //Given
        int month = 20;
        int year = 1980;

        //When Then
        boolean isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertFalse(isCorrect);

        month = 0;
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertFalse(isCorrect);

        month = 13;
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertFalse(isCorrect);

        month = -5;
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertFalse(isCorrect);

        month = 12;
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertTrue(isCorrect);

        year = 1979;
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertFalse(isCorrect);

        year = LocalDate.now().getYear();
        isCorrect = ToolsManager.isMonthCorrect(month,year);
        assertTrue(isCorrect);
    }

    @Test
    public void isTheSameEnum() {
        //Given
        TransactionType enum1 = TransactionType.EXPENSES;
        TransactionType enum2 = TransactionType.REVENUES;

        //When Then
        boolean isCorrect = ToolsManager.isTheSameEnum(enum1,enum2);
        assertFalse(isCorrect);

        isCorrect = ToolsManager.isTheSameEnum(enum1,null);
        assertFalse(isCorrect);

        enum2 = TransactionType.EXPENSES;
        isCorrect = ToolsManager.isTheSameEnum(enum1,enum2);
        assertTrue(isCorrect);
    }

    @Test
    public void sortByType(){
        //Given
        CategoryDto category = new CategoryDto();
        category.setType(TransactionType.EXPENSES);
        TransactionDto dtoE = new TransactionDto.TransactionDtoBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.EXPENSES)
                .amount(50)
                .category(category).build();

        category.setType(TransactionType.REVENUES);
        TransactionDto dtoR = new TransactionDto.TransactionDtoBuilder()
                .title("Test")
                .description("Test Description")
                .date(Date.valueOf(LocalDate.now()))
                .type(TransactionType.REVENUES)
                .amount(50)
                .category(category).build();

        List<TransactionDto> dtos = new ArrayList<>();
        dtos.add(dtoE);
        dtos.add(dtoE);
        dtos.add(dtoR);
        dtos.add(dtoR);
        dtos.add(dtoR);

        //When
        List<TransactionDto> all = ToolsManager.sortByType(dtos,"");
        List<TransactionDto> revenues = ToolsManager.sortByType(dtos,"REV");
        List<TransactionDto> expenses = ToolsManager.sortByType(dtos,"EXP");

        //Then
        assertEquals(5,all.size());
        assertEquals(3,revenues.size());
        assertEquals(2,expenses.size());
        for (TransactionDto dto: revenues) {
            assertEquals(TransactionType.REVENUES,dto.getType());
        }
        for (TransactionDto dto: expenses) {
            assertEquals(TransactionType.EXPENSES,dto.getType());
        }
    }
}