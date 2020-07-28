package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
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
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void create() {
        //When
        Category category = categoryRepository.save(factory.category());
        CategoryDto fromDbDto = mapper.mapToDto(category);
        TransactionDto transactionDto = factory.transactionDto();
        transactionDto.setDate(Date.valueOf("2020-06-20"));
        transactionDto.setCategoryDto(fromDbDto);
        TransactionDto fromDb = transactionService.create(transactionDto);
        transactionRepository.deleteById(fromDb.getId());
        categoryRepository.delete(category);

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(category.getId()));
        assertNotNull(fromDb.getId());
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test Description",fromDb.getDescription());
        assertEquals(TransactionType.REVENUES,fromDb.getType());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.getDate());
        assertEquals(50,fromDb.getAmount(),0);
    }

    @Test
    public void getAll() {
        //Given
        List<Transaction> transactions = transactionDefaultList();
        for (Transaction transaction : transactions){
            transactionRepository.save(transaction);
        }

        //When
        List<TransactionDto> transactionDtos = transactionService.getAll();
        for (TransactionDto transaction: transactionDtos) {
            transactionRepository.deleteById(transaction.getId());
        }
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        assertFalse(categoryRepository.existsById(transactionDtos.get(0).getCategoryDto().getId()));
        for (TransactionDto transaction: transactionDtos) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        assertEquals(3,transactionDtos.size());
    }

    @Test
    public void get() {
        //Given
        Category formDb = categoryRepository.save(factory.category());
        Transaction transaction = factory.transaction();
        transaction.setDate(Date.valueOf("2020-06-20"));
        transaction.setCategory(formDb);

        Transaction toDb = transactionRepository.save(transaction);
        long transactionId = toDb.getId();

        //When
        TransactionDto fromDb = transactionService.get(transactionId);
        transactionRepository.delete(toDb);
        categoryRepository.delete(toDb.getCategory());

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategoryDto().getId()));
        assertEquals(transactionId,fromDb.getId(),0);
        assertEquals("Test",fromDb.getTitle());
        assertEquals("Test Description",fromDb.getDescription());
        assertEquals(TransactionType.REVENUES,fromDb.getType());
        assertEquals(Date.valueOf("2020-06-20"),fromDb.getDate());
        assertEquals(50,fromDb.getAmount(),0);
    }

    @Test
    public void update() {
        //Given
        Category formDb = categoryRepository.save(factory.category());
        Transaction transaction = factory.transaction();
        transaction.setCategory(formDb);
        Transaction toDb = transactionRepository.save(transaction);
        long transactionId = toDb.getId();
        CategoryDto categoryDto = mapper.mapToDto(formDb);

        TransactionDto transactionDto = new TransactionDto.TransactionDtoBuilder()
                .id(transactionId)
                .title("Test beta")
                .type(TransactionType.REVENUES)
                .description("Test Description beta")
                .date(Date.valueOf("2020-05-05"))
                .amount(40)
                .category(categoryDto).build();

        //When
        TransactionDto updated =transactionService.update(transactionDto);
        transactionRepository.deleteById(updated.getId());
        categoryRepository.delete(toDb.getCategory());

        //Then
        assertFalse(transactionRepository.existsById(updated.getId()));
        assertFalse(categoryRepository.existsById(updated.getCategoryDto().getId()));
        assertEquals(toDb.getCategory().getId(),updated.getCategoryDto().getId());
        assertEquals(transactionId,updated.getId(),0);
        assertEquals("Test beta",updated.getTitle());
        assertEquals("Test Description beta",updated.getDescription());
        assertEquals(TransactionType.REVENUES,updated.getType());
        assertEquals(Date.valueOf("2020-05-05"),updated.getDate());
        assertEquals(40,updated.getAmount(),0);
    }

    @Test
    public void delete() {
        //Given
        Category category = categoryRepository.save(factory.category());
        Transaction transaction = factory.transaction();
        transaction.setCategory(category);
        Transaction fromDb = transactionRepository.save(transaction);

        //When
        transactionService.delete(fromDb.getId());
        categoryRepository.delete(fromDb.getCategory());

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
    }

    @Test
    public void findByDate() {
        //Given
        List<Transaction> transactions = givenFindByDate();

        //When
        List<TransactionDto> fromDb = transactionService.findByDate("2020-06-20");
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        for (TransactionDto transaction: fromDb) {
            assertEquals(transaction.getDate(), Date.valueOf("2020-06-20"));
            assertFalse(transactionRepository.existsById(transaction.getId()));
            assertFalse(categoryRepository.existsById(transaction.getCategoryDto().getId()));
        }
        assertEquals(2,fromDb.size());
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
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
            assertFalse(categoryRepository.existsById(transaction.getCategory().getId()));
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
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
            assertFalse(categoryRepository.existsById(transaction.getCategory().getId()));
        }
        assertEquals(2,transactionDtos.size());
    }

    @Test
    public void selectedMonth() {
        //Given
        List<Transaction> transactions = givenSelectedMonth();

        //When
        List<TransactionDto> transactionDtos = transactionService.selectedMonth("2019-05");
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
            assertFalse(categoryRepository.existsById(transaction.getCategory().getId()));
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
        List<Transaction> transactions = givenBetweenDate();

        //When
        List<TransactionDto> transactionDtos = transactionService.betweenDate("2019-03-05","2019-05-20");
        for (Transaction transaction: transactions) {
            transactionRepository.delete(transaction);
        }
        categoryRepository.delete(transactions.get(0).getCategory());

        //Then
        for (Transaction transaction: transactions) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
            assertFalse(categoryRepository.existsById(transaction.getCategory().getId()));
        }
        assertEquals(2,transactionDtos.size());
    }

    private List<Transaction> givenFindByDate(){
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> baseList = transactionDefaultList();
        baseList.get(0).setDate(Date.valueOf("2020-06-20"));
        baseList.get(1).setDate(Date.valueOf("2020-06-20"));

        for (Transaction transaction: baseList) {
            Transaction formDb = transactionRepository.save(transaction);
            transactions.add(formDb);
        }

        return transactions;
    }

    private List<Transaction> givenThisWeek(){
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> baseList = transactionDefaultList();

        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        baseList.get(0).setDate(Date.valueOf("2020-06-20"));
        if (dayOfWeek - 2 <= 0){
            baseList.get(1).setDate(Date.valueOf(LocalDate.now()));
        }else {
            baseList.get(1).setDate(Date.valueOf(LocalDate.now().minusDays(2)));
        }

        for (Transaction transaction: baseList) {
            Transaction formDb = transactionRepository.save(transaction);
            transactions.add(formDb);
        }

        return transactions;
    }

    private List<Transaction> givenThisMonth(){
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> baseList = transactionDefaultList();

        int dayOfMonth = LocalDate.now().getDayOfMonth();

        baseList.get(0).setDate(Date.valueOf("2020-06-20"));
        if (dayOfMonth - 8 <= 0){
            baseList.get(1).setDate(Date.valueOf(LocalDate.now()));
        }else {
            baseList.get(1).setDate(Date.valueOf(LocalDate.now().minusDays(8)));
        }

        for (Transaction transaction: baseList) {
            Transaction formDb = transactionRepository.save(transaction);
            transactions.add(formDb);
        }

        return transactions;
    }

    private List<Transaction> givenSelectedMonth(){
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> baseList = transactionDefaultList();

        baseList.get(0).setDate(Date.valueOf("2019-05-25"));
        baseList.get(1).setDate(Date.valueOf("2019-05-04"));

        for (Transaction transaction: baseList) {
            Transaction formDb = transactionRepository.save(transaction);
            transactions.add(formDb);
        }

        return transactions;
    }

    private List<Transaction> givenBetweenDate(){
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> baseList = transactionDefaultList();

        baseList.get(0).setDate(Date.valueOf("2019-03-25"));
        baseList.get(1).setDate(Date.valueOf("2019-05-04"));

        for (Transaction transaction: baseList) {
            Transaction formDb = transactionRepository.save(transaction);
            transactions.add(formDb);
        }

        return transactions;
    }

    private List<Transaction> transactionDefaultList(){
        List<Transaction> transactions = new ArrayList<>();
        Category fromDb = categoryRepository.save(factory.category());

        for (int i = 1; i <= 3; i++){
            Transaction transaction = factory.transaction();
            transaction.setCategory(fromDb);
            transactions.add(transaction);
        }

        return transactions;
    }
}