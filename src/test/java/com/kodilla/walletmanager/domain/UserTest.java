package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        String toString = "User{" + "id=" + formDb.getId() + ", emile='" + formDb.getEmile() + "', balance=" + formDb.getBalance() + '}';

        //Then
        assertTrue(repository.existsById(formDb.getId()));
        assertEquals(-150.0,formDb.getBalance(),0);
        assertEquals("test@email.com",user.getEmile());
        assertEquals(toString,formDb.toString());
        repository.delete(formDb);
        assertFalse(repository.existsById(formDb.getId()));
    }
}