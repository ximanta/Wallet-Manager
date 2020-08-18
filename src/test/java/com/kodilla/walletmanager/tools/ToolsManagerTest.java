/*
package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
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
    public void sortByTypeT(){
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
        List<TransactionDto> all = ToolsManager.sortByTypeT(dtos,"");
        List<TransactionDto> revenues = ToolsManager.sortByTypeT(dtos,"REV");
        List<TransactionDto> expenses = ToolsManager.sortByTypeT(dtos,"EXP");

        //Then
        assertEquals(0,all.size());
        assertEquals(3,revenues.size());
        assertEquals(2,expenses.size());
        for (TransactionDto dto: revenues) {
            assertEquals(TransactionType.REVENUES,dto.getType());
        }
        for (TransactionDto dto: expenses) {
            assertEquals(TransactionType.EXPENSES,dto.getType());
        }
    }

    @Test
    public void sortByTypeC(){
        //Given
        CategoryDto category1 = new CategoryDto();
        CategoryDto category2 = new CategoryDto();
        CategoryDto category3 = new CategoryDto();
        CategoryDto category4 = new CategoryDto();
        CategoryDto category5 = new CategoryDto();
        category1.setType(TransactionType.EXPENSES);
        category2.setType(TransactionType.EXPENSES);
        category3.setType(TransactionType.REVENUES);
        category4.setType(TransactionType.REVENUES);
        category5.setType(TransactionType.REVENUES);

        List<CategoryDto> dtos = new ArrayList<>();
        dtos.add(category1);
        dtos.add(category2);
        dtos.add(category3);
        dtos.add(category4);
        dtos.add(category5);

        //When
        List<CategoryDto> all = ToolsManager.sortByTypeC(dtos,"");
        List<CategoryDto> revenues = ToolsManager.sortByTypeC(dtos,"REV");
        List<CategoryDto> expenses = ToolsManager.sortByTypeC(dtos,"EXP");

        //Then
        assertEquals(0,all.size());
        assertEquals(3,revenues.size());
        assertEquals(2,expenses.size());
        for (CategoryDto dto: revenues) {
            assertEquals(TransactionType.REVENUES,dto.getType());
        }
        for (CategoryDto dto: expenses) {
            assertEquals(TransactionType.EXPENSES,dto.getType());
        }
    }
}*/
