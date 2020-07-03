package com.kodilla.walletmanager.repository;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void findByDate() {
        //Given
        Date correct = Date.valueOf("2020-05-05");
        Date incorrect = Date.valueOf("2020-05-06");

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test 1");
        transaction1.setDate(correct);
        transaction1.setType(TransactionType.REVENUES);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test 2");
        transaction2.setDate(correct);
        transaction2.setType(TransactionType.EXPENSES);

        Transaction transaction3 = new Transaction();
        transaction3.setTitle("Test 3");
        transaction3.setDate(incorrect);
        transaction3.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);


        //When
        List<Transaction> list= transactionRepository.findByDate(correct);

        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertEquals(2,list.size());
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
    }

    @Test
    public void thisWeek() {
        //Given
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = localDate1.minusDays(10);
        LocalDate localDate3 = localDate1.minusDays(12);

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test 1");
        transaction1.setDate(Date.valueOf(localDate1));
        transaction1.setType(TransactionType.EXPENSES);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test 2");
        transaction2.setDate(Date.valueOf(localDate2));
        transaction2.setType(TransactionType.REVENUES);

        Transaction transaction3 = new Transaction();
        transaction3.setTitle("Test 3");
        transaction3.setDate(Date.valueOf(localDate3));
        transaction3.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        //When
        List<Transaction> list= transactionRepository.thisWeek();

        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertEquals(1,list.size());
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
    }

    @Test
    public void thisMonth() {
        //Given
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test 1");
        transaction1.setDate(Date.valueOf(now));
        transaction1.setType(TransactionType.EXPENSES);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test 2");
        transaction2.setDate(Date.valueOf(lastMonth));
        transaction2.setType(TransactionType.REVENUES);

        Transaction transaction3 = new Transaction();
        transaction3.setTitle("Test 3");
        transaction3.setDate(Date.valueOf(now));
        transaction3.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        //When
        List<Transaction> list= transactionRepository.thisMonth();

        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertEquals(2,list.size());
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
    }

    @Test
    public void selectedMonth() {
        //Given
        Date correct = Date.valueOf("2020-05-05");
        Date incorrect = Date.valueOf("2020-08-05");

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test 1");
        transaction1.setDate(correct);
        transaction1.setType(TransactionType.EXPENSES);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test 2");
        transaction2.setDate(correct);
        transaction2.setType(TransactionType.REVENUES);

        Transaction transaction3 = new Transaction();
        transaction3.setTitle("Test 3");
        transaction3.setDate(incorrect);
        transaction3.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        //When
        List<Transaction> list= transactionRepository.selectedMonth(5,2020);

        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertEquals(2,list.size());
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
    }

    @Test
    public void betweenDate() {
        //Given
        Date date1 = Date.valueOf("2020-03-05");
        Date date2 = Date.valueOf("2020-05-05");
        Date date3 = Date.valueOf("2020-07-05");

        Transaction transaction1 = new Transaction();
        transaction1.setTitle("Test 1");
        transaction1.setDate(date1);
        transaction1.setType(TransactionType.EXPENSES);

        Transaction transaction2 = new Transaction();
        transaction2.setTitle("Test 2");
        transaction2.setDate(date2);
        transaction2.setType(TransactionType.REVENUES);

        Transaction transaction3 = new Transaction();
        transaction3.setTitle("Test 3");
        transaction3.setDate(date3);
        transaction3.setType(TransactionType.EXPENSES);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        //When
        List<Transaction> list= transactionRepository.betweenDate(Date.valueOf("2020-03-02"),Date.valueOf("2020-05-10"));

        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertEquals(2,list.size());
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
    }
}