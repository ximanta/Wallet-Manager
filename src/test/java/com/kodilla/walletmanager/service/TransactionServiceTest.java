package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.builders.TransactionDtoBuilder;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.dto.UserLoginPassword;
import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClassesFactory factory;

    @Test
    public void create() {
        //When
        Category category = categoryRepository.save(factory.category());
        User user = saveDuplicate(factory.user());
        CategoryDto fromDbDto = categoryMapper.mapToDto(category);
        UserDto userDto = userMapper.mapToDto(user);
        TransactionDto transactionDto = factory.transactionDto();
        transactionDto.setDate(Date.valueOf("2020-06-20"));
        transactionDto.setCategoryDto(fromDbDto);
        transactionDto.setUserDto(userDto);
        TransactionDto fromDb = transactionService.create(transactionDto);
        transactionRepository.deleteById(fromDb.getId());
        categoryRepository.delete(category);
        userRepository.delete(user);

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(category.getId()));
        assertFalse(userRepository.existsById(user.getId()));
        assertNotNull(fromDb.getId());
        assertNotNull(fromDb.getCategoryDto());
        assertNotNull(fromDb.getUserDto());
        assertEquals("Test", fromDb.getTitle());
        assertEquals("Test Description", fromDb.getDescription());
        assertEquals(TransactionType.REVENUES, fromDb.getType());
        assertEquals(Date.valueOf("2020-06-20"), fromDb.getDate());
        assertEquals(50, fromDb.getAmount(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void vailCreate(){
        TransactionDto transaction = factory.transactionDto();
        transactionService.create(transaction);
    }

    @Test
    public void getAll() {
        //Given
        List<Transaction> transactions = transactionDefaultList();
        User user = transactions.get(0).getUser();
        UserLoginPassword loginPassword = new UserLoginPassword(user.getLogin(), user.getPassword());
        for (Transaction transaction : transactions) {
            transactionRepository.save(transaction);
        }

        //When
        List<TransactionDto> transactionDtos = transactionService.getAll(loginPassword);
        for (TransactionDto transaction : transactionDtos) {
            transactionRepository.deleteById(transaction.getId());
        }
        categoryRepository.delete(transactions.get(0).getCategory());
        userRepository.delete(user);

        //Then
        assertFalse(categoryRepository.existsById(transactionDtos.get(0).getCategoryDto().getId()));
        assertFalse(userRepository.existsById(user.getId()));
        for (TransactionDto transaction : transactionDtos) {
            assertFalse(transactionRepository.existsById(transaction.getId()));
        }
        assertEquals(3, transactionDtos.size());
    }

    @Test(expected = RuntimeException.class)
    public void validGetAll() {
        List<TransactionDto> dtos = transactionService.getAll(new UserLoginPassword("Test","Test"));
    }

    @Test
    public void update() {
        //Given
        Category formDb = categoryRepository.save(factory.category());
        User user = saveDuplicate(factory.user());
        Transaction transaction = factory.transaction();
        transaction.setCategory(formDb);
        transaction.setUser(user);
        Transaction toDb = transactionRepository.save(transaction);
        long transactionId = toDb.getId();
        CategoryDto categoryDto = categoryMapper.mapToDto(formDb);
        UserDto userDto = userMapper.mapToDto(user);

        TransactionDto transactionDto = new TransactionDtoBuilder()
                .id(transactionId)
                .title("Test beta")
                .type(TransactionType.REVENUES)
                .description("Test Description beta")
                .date(Date.valueOf("2020-05-05"))
                .amount(40)
                .category(categoryDto)
                .user(userDto).build();

        //When
        TransactionDto updated = transactionService.update(transactionDto);
        transactionRepository.deleteById(updated.getId());
        categoryRepository.delete(toDb.getCategory());

        //Then
        assertFalse(transactionRepository.existsById(updated.getId()));
        assertFalse(categoryRepository.existsById(updated.getCategoryDto().getId()));
        assertEquals(toDb.getCategory().getId(), updated.getCategoryDto().getId());
        assertEquals(transactionId, updated.getId(), 0);
        assertEquals("Test beta", updated.getTitle());
        assertEquals("Test Description beta", updated.getDescription());
        assertEquals(TransactionType.REVENUES, updated.getType());
        assertEquals(Date.valueOf("2020-05-05"), updated.getDate());
        assertEquals(40, updated.getAmount(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void validUpdate(){
        transactionService.update(factory.transactionDto());
    }

    @Test
    public void delete() {
        //Given
        Category category = categoryRepository.save(factory.category());
        User user = saveDuplicate(factory.user());
        Transaction transaction = factory.transaction();
        transaction.setCategory(category);
        transaction.setUser(user);
        Transaction fromDb = transactionRepository.save(transaction);

        //When
        transactionService.delete(fromDb.getId());
        categoryRepository.delete(fromDb.getCategory());
        userRepository.delete(user);

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
        assertFalse(userRepository.existsById(fromDb.getUser().getId()));
    }

    @Test(expected = RuntimeException.class)
    public void validDelete(){
        transactionService.delete(-10);
    }

    /*
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
    */
    private List<Transaction> transactionDefaultList(){
        List<Transaction> transactions = new ArrayList<>();
        Category fromDb = categoryRepository.save(factory.category());
        User user = saveDuplicate(factory.user());

        for (int i = 1; i <= 3; i++){
            Transaction transaction = factory.transaction();
            transaction.setCategory(fromDb);
            transaction.setUser(user);
            transactions.add(transaction);
        }

        return transactions;
    }

    private User saveDuplicate(User user){
        Optional<User> optional = userRepository.getByLogin(user.getLogin());
        if (!optional.isPresent()){
            return userRepository.save(user);
        }else {
            return optional.get();
        }
    }

}
