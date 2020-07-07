package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.mapper.TransactionMapper;
import com.kodilla.walletmanager.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionMapper transactionMapper;

    @Test
    public void create() {
        //When
        TransactionDto fromDb = transactionService.create(createDto());
        transactionRepository.deleteById(fromDb.getId());

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertNotNull(fromDb.getId());
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test Description",fromDb.getDescription());
        assertEquals(TransactionType.EXPENSES,fromDb.getType());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.getDate());
        assertEquals(25,fromDb.getAmount(),0);
    }

    @Test
    public void getAll() {
        //Given
        Transaction transaction1 = transactionRepository.save(createEntity());
        Transaction transaction2 = transactionRepository.save(createEntity());
        Transaction transaction3 = transactionRepository.save(createEntity());

        //When
        List<TransactionDto> transactionDtos = transactionService.getAll();
        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.delete(transaction3);

        //Then
        assertFalse(transactionRepository.existsById(transaction1.getId()));
        assertFalse(transactionRepository.existsById(transaction2.getId()));
        assertFalse(transactionRepository.existsById(transaction3.getId()));
        assertEquals(3,transactionDtos.size());
    }

    @Test
    public void get() {
        //Given
        Transaction transaction = transactionRepository.save(createEntity());
        long transactionId = transaction.getId();

        //When
        TransactionDto fromDb = transactionService.get(transactionId);
        transactionRepository.delete(transaction);

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertEquals(transactionId,fromDb.getId(),0);
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test Description",fromDb.getDescription());
        assertEquals(TransactionType.EXPENSES,fromDb.getType());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.getDate());
        assertEquals(25,fromDb.getAmount(),0);
    }

    @Test
    public void update() {
        //Given
        Transaction transaction = transactionRepository.save(createEntity());
        long transactionId = transaction.getId();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transactionId);
        transactionDto.setTitle("Test beta");
        transactionDto.setDescription("Test Description beta");
        transactionDto.setType(TransactionType.EXPENSES);
        transactionDto.setDate(Date.valueOf("2020-05-05"));
        transactionDto.setAmount(40);

        //When
        transactionService.update(transactionDto);
        Transaction formDb = transactionRepository.findById(transactionId).get();
        transactionRepository.deleteById(formDb.getId());

        //Then
        assertFalse(transactionRepository.existsById(formDb.getId()));
        assertEquals(transactionId,formDb.getId(),0);
        assertEquals("Test beta",formDb.getTitle());
        assertEquals("Test Description beta",formDb.getDescription());
        assertEquals(TransactionType.EXPENSES,formDb.getType());
        assertEquals(Date.valueOf("2020-05-05"),formDb.getDate());
        assertEquals(40,formDb.getAmount(),0);
    }

    @Test
    public void delete() {
        //Given
        Transaction transaction = transactionRepository.save(createEntity());
        long transactionId = transaction.getId();

        //When
        transactionService.delete(transactionId);

        //Then
        assertFalse(transactionRepository.existsById(transactionId));
    }

    @Test
    public void findByDate() {
        //Given
        Transaction transaction1 = transactionRepository.save(createEntity());
        Transaction transaction2 = transactionRepository.save(createEntity());

        //When
        List<TransactionDto> fromDb = transactionService.findByDate(Date.valueOf("2020-06-20"));
        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);

        long transaction1Id = transaction1.getId();
        long transaction2Id = transaction2.getId();

        //Then
        assertFalse(transactionRepository.existsById(transaction1Id));
        assertFalse(transactionRepository.existsById(transaction2Id));
        assertEquals(2,fromDb.size());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.get(0).getDate());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.get(1).getDate());
    }

    @Test
    public void thisWeek() {
        //Given
        List<Transaction> transactions = givenThisWeek();

        //When
        List<TransactionDto> transactionDtos = transactionService.thisWeek();
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        assertEquals(2,transactionDtos.size());
    }

    @Test
    public void thisMonth() {
        //Given
        List<Transaction> transactions = givenThisMonth();

        //When
        List<TransactionDto> transactionDtos = transactionService.thisMonth();
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        assertEquals(2,transactionDtos.size());
    }

    @Test
    public void selectedMonth() {
        //Given
        List<Transaction> transactions = givenSelectedMonth();

        //When
        List<TransactionDto> transactionDtos = transactionService.selectedMonth(5,2019);
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        for (TransactionDto transactionDto: transactionDtos) {
            long monthNumber = transactionDto.getDate().toLocalDate().getMonthValue();
            long yearNumber = transactionDto.getDate().toLocalDate().getYear();
            assertTrue(monthNumber == 5 && yearNumber == 2019);
        }
        assertEquals(2,transactionDtos.size());
    }

    @Test
    public void betweenDate() {
        //Given
        List<Transaction> transactions = givenBetweenMonth();
        Date date1 = Date.valueOf("2019-03-05");
        Date date2 = Date.valueOf("2019-05-20");

        //When
        List<TransactionDto> transactionDtos = transactionService.betweenDate(date1,date2);
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        assertEquals(2,transactionDtos.size());
    }

    private TransactionDto createDto(){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTitle("Test");
        transactionDto.setDescription("Test Description");
        transactionDto.setType(TransactionType.EXPENSES);
        transactionDto.setDate(Date.valueOf("2020-06-20"));
        transactionDto.setAmount(25);

        return transactionDto;
    }

    private Transaction createEntity(){
        return transactionMapper.mapToEntity(createDto());
    }

    private List<Transaction> givenThisWeek(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = createEntity();
        Transaction transaction2 = createEntity();
        Transaction transaction3 = createEntity();

        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        transaction1.setDate(Date.valueOf(LocalDate.now()));

        if (dayOfWeek - 2 <= 0){
            transaction2.setDate(Date.valueOf(LocalDate.now()));
        }else {
            transaction2.setDate(Date.valueOf(LocalDate.now().minusDays(2)));
        }

        Transaction formDb1 = transactionRepository.save(transaction1);
        Transaction formDb2 = transactionRepository.save(transaction2);
        Transaction formDb3 = transactionRepository.save(transaction3);

        transactions.add(formDb1);
        transactions.add(formDb2);
        transactions.add(formDb3);

        return transactions;
    }

    private List<Transaction> givenThisMonth(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = createEntity();
        Transaction transaction2 = createEntity();
        Transaction transaction3 = createEntity();

        int dayOfMonth = LocalDate.now().getDayOfMonth();

        transaction1.setDate(Date.valueOf(LocalDate.now()));
        if (dayOfMonth - 8 <= 0){
            transaction2.setDate(Date.valueOf(LocalDate.now()));
        }else {
            transaction2.setDate(Date.valueOf(LocalDate.now().minusDays(8)));
        }

        Transaction formDb1 = transactionRepository.save(transaction1);
        Transaction formDb2 = transactionRepository.save(transaction2);
        Transaction formDb3 = transactionRepository.save(transaction3);

        transactions.add(formDb1);
        transactions.add(formDb2);
        transactions.add(formDb3);

        return transactions;
    }

    private List<Transaction> givenSelectedMonth(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = createEntity();
        Transaction transaction2 = createEntity();
        Transaction transaction3 = createEntity();

        transaction1.setDate(Date.valueOf("2019-05-25"));
        transaction2.setDate(Date.valueOf("2019-05-04"));

        Transaction formDb1 = transactionRepository.save(transaction1);
        Transaction formDb2 = transactionRepository.save(transaction2);
        Transaction formDb3 = transactionRepository.save(transaction3);

        transactions.add(formDb1);
        transactions.add(formDb2);
        transactions.add(formDb3);

        return transactions;
    }

    private List<Transaction> givenBetweenMonth(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = createEntity();
        Transaction transaction2 = createEntity();
        Transaction transaction3 = createEntity();

        transaction1.setDate(Date.valueOf("2019-03-25"));
        transaction2.setDate(Date.valueOf("2019-05-04"));

        Transaction formDb1 = transactionRepository.save(transaction1);
        Transaction formDb2 = transactionRepository.save(transaction2);
        Transaction formDb3 = transactionRepository.save(transaction3);

        transactions.add(formDb1);
        transactions.add(formDb2);
        transactions.add(formDb3);

        return transactions;
    }
}