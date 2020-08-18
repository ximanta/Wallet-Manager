package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.builders.TransactionDtoBuilder;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.dto.UserCertifying;
import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
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
import java.time.LocalDate;
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
        System.out.println(category);
        User user = saveDuplicate(factory.user());
        CategoryDto fromDbDto = categoryMapper.mapToDto(category);
        UserDto userDto = userMapper.mapToDto(user);
        TransactionDto transactionDto = factory.transactionDto();
        transactionDto.setDate(Date.valueOf("2020-06-20"));
        transactionDto.setCategoryDto(fromDbDto);
        transactionDto.setUserDto(userDto);
        transactionDto.setCurrencyType(CurrencyType.EUR);
        TransactionDto fromDb = transactionService.create(transactionDto);
        System.out.println(fromDb);
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
        assertEquals(Date.valueOf("2020-06-20"), fromDb.getDate());
        assertNotEquals(50, fromDb.getAmount(), 0);
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
        UserCertifying loginPassword = new UserCertifying(user.getLogin(), user.getPassword());
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
        List<TransactionDto> dtos = transactionService.getAll(new UserCertifying("Test","Test"));
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
                .description("Test Description beta")
                .date(Date.valueOf("2020-05-05"))
                .amount(40)
                .currencyType(CurrencyType.EUR)
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
        assertEquals(Date.valueOf("2020-05-05"), updated.getDate());
        assertNotEquals(40, updated.getAmount(), 0);
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
        UserCertifying certifying = new UserCertifying(user.getLogin(),user.getPassword());
        Transaction transaction = factory.transaction();
        transaction.setCategory(category);
        transaction.setUser(user);
        Transaction fromDb = transactionRepository.save(transaction);

        //When
        transactionService.delete(fromDb.getId(),certifying);
        categoryRepository.delete(fromDb.getCategory());
        userRepository.delete(user);

        //Then
        assertFalse(transactionRepository.existsById(fromDb.getId()));
        assertFalse(categoryRepository.existsById(fromDb.getCategory().getId()));
        assertFalse(userRepository.existsById(fromDb.getUser().getId()));
    }

    @Test(expected = RuntimeException.class)
    public void validDelete(){
        transactionService.delete(-10,null);
    }

        @Test
        public void thisWeek() {
            //Given
            List<Transaction> transactions = givenThisWeek();
            UserCertifying certifying = new UserCertifying(transactions.get(0).getUser().getLogin(),transactions.get(0).getUser().getPassword());

            //When
            List<TransactionDto> transactionDtos = transactionService.thisWeek(certifying);
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
            UserCertifying certifying = new UserCertifying(transactions.get(0).getUser().getLogin(),transactions.get(0).getUser().getPassword());

            //When
            List<TransactionDto> transactionDtos = transactionService.thisMonth(certifying);
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
        public void betweenDate() {
            //Given
            List<Transaction> transactions = givenBetweenDate();
            UserCertifying certifying = new UserCertifying(transactions.get(0).getUser().getLogin(),transactions.get(0).getUser().getPassword());

            //When
            List<TransactionDto> transactionDtos = transactionService.betweenDate("2019-03-05","2019-05-20",certifying);
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
