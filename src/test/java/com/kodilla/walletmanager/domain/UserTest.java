package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest  {
    @Autowired
    UserRepository repository;

    @Autowired
    ClassesFactory factory;

    @Test
    public void createCompleteRecordTest(){
        //Given
        User user = factory.user();
        //When
        User formDb = repository.save(user);
        String toString = "User{" +
                "id=" + user.getId() +
                ", login='" + user.getLogin() + '\'' +
                ", password='" + user.getPassword() + '\'' +
                ", emile='" + user.getEmile() + '\'' +
                ", birthDate=" + user.getBirthDate() +
                ", active=" + user.isActive() +
                ", currencyType=" + user.getCurrencyType() +
                ", balance=" + user.getBalance() +
                '}';

        //Then
        assertTrue(repository.existsById(formDb.getId()));
        assertTrue(formDb.isActive());
        assertEquals("Test",formDb.getLogin());
        assertEquals("Password",formDb.getPassword());
        assertEquals("test@email.com",formDb.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),formDb.getBirthDate());
        assertEquals(CurrencyType.USD,user.getCurrencyType());
        assertEquals(-150.0,formDb.getBalance(),0);
        assertEquals(toString,formDb.toString());

        repository.delete(formDb);
        assertFalse(repository.existsById(formDb.getId()));
    }

    @Test(expected = TransactionSystemException.class)
    public void createIncompleteRecordTest(){
        User user = new User();
        User fromDb = repository.save(user);
        assertFalse(repository.existsById(fromDb.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void checkLoginUniqueMechanic(){
        //Given
        for (int i = 0; i <= 5; i++){
            repository.save(factory.user());
        }
        //When
        List<User> users = repository.findAll();
        List<User> duplicate = new ArrayList<>();
        for (User user: users) {
            if (user.getLogin().equals("Test")) {
                duplicate.add(user);
            }
        }
        //Then
        assertEquals(1,duplicate.size());
    }
}