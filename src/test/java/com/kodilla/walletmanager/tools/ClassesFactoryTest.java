package com.kodilla.walletmanager.tools;

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

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassesFactoryTest {
    @Autowired
    ClassesFactory classesFactory;

    @Test
    public void makeTransaction() {
        //Given When
        LocalDate today = LocalDate.now();
        Transaction complete = classesFactory.makeTransaction(ClassesFactory.COMPLETE);
        Transaction incomplete = classesFactory.makeTransaction(ClassesFactory.INCOMPLETE);

        //Then
        assertNull(complete.getId());
        assertNotNull(complete.getCategory());
        assertEquals("Test",complete.getTitle());
        assertEquals("Test Description",complete.getDescription());
        assertEquals(50,complete.getAmount(),0);
        assertEquals(TransactionType.REVENUES,complete.getType());
        assertEquals(today.getDayOfMonth(),complete.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),complete.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),complete.getDate().toLocalDate().getYear());

        assertNull(incomplete.getId());
        assertNotNull(incomplete.getCategory());
        assertEquals("Test",incomplete.getTitle());
        assertNull(incomplete.getDescription());
        assertEquals(0,incomplete.getAmount(),0);
        assertEquals(TransactionType.REVENUES,incomplete.getType());
        assertEquals(today.getDayOfMonth(),incomplete.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),incomplete.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),incomplete.getDate().toLocalDate().getYear());
    }

    @Test
    public void makeTransactionDto() {
        //Given When
        LocalDate today = LocalDate.now();
        TransactionDto complete = classesFactory.makeTransactionDto(ClassesFactory.COMPLETE);
        TransactionDto incomplete = classesFactory.makeTransactionDto(ClassesFactory.INCOMPLETE);

        //Then
        assertNull(complete.getId());
        assertNotNull(complete.getCategoryDto());
        assertEquals("Test",complete.getTitle());
        assertEquals("Test Description",complete.getDescription());
        assertEquals(50,complete.getAmount(),0);
        assertEquals(TransactionType.REVENUES,complete.getType());
        assertEquals(today.getDayOfMonth(),complete.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),complete.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),complete.getDate().toLocalDate().getYear());

        assertNull(incomplete.getId());
        assertNotNull(incomplete.getCategoryDto());
        assertEquals("Test",incomplete.getTitle());
        assertNull(incomplete.getDescription());
        assertEquals(0,incomplete.getAmount(),0);
        assertEquals(TransactionType.REVENUES,incomplete.getType());
        assertEquals(today.getDayOfMonth(),incomplete.getDate().toLocalDate().getDayOfMonth());
        assertEquals(today.getMonthValue(),incomplete.getDate().toLocalDate().getMonthValue());
        assertEquals(today.getYear(),incomplete.getDate().toLocalDate().getYear());
    }

    @Test
    public void makeCategory() {
        //Given When
        Category complete = classesFactory.makeCategory(ClassesFactory.COMPLETE);
        Category incomplete = classesFactory.makeCategory(ClassesFactory.INCOMPLETE);

        //Then
        assertNull(complete.getId());
        assertEquals("Test",complete.getName());
        assertEquals(TransactionType.REVENUES,complete.getType());
        assertNotNull(complete.getTransactions());

        assertNull(incomplete.getId());
        assertEquals("Test",incomplete.getName());
        assertEquals(TransactionType.REVENUES,incomplete.getType());
        assertNull(incomplete.getTransactions());
    }

    @Test
    public void makeCategoryDto() {
        //Given When
        CategoryDto complete = classesFactory.makeCategoryDto(ClassesFactory.COMPLETE);
        CategoryDto incomplete = classesFactory.makeCategoryDto(ClassesFactory.INCOMPLETE);

        //Then
        assertNull(complete.getId());
        assertEquals("Test",complete.getName());
        assertEquals(TransactionType.REVENUES,complete.getType());
        assertNotNull(complete.getTransactionDtos());

        assertNull(incomplete.getId());
        assertEquals("Test",incomplete.getName());
        assertEquals(TransactionType.REVENUES,incomplete.getType());
        assertNull(incomplete.getTransactionDtos());
    }
}