package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.repository.UserRepository;
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

    @Test
    public void createCompleteRecordTest(){
        //Given
        User user = new User();
        user.setEmile("test@email.com");
        user.setBalance(-250);

        //When
        User formDb = repository.save(user);
        String toString = "User{" + "id=" + formDb.getId() + ", emile='" + formDb.getEmile() + "', balance=" + formDb.getBalance() + '}';

        //Then
        assertTrue(repository.existsById(formDb.getId()));
        assertEquals(-250.0,formDb.getBalance(),0);
        assertEquals("test@email.com",user.getEmile());
        assertEquals(toString,formDb.toString());
    }
}