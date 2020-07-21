package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.User;
import com.kodilla.walletmanager.dto.UserDto;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository repository;

    @Autowired
    UserMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Autowired
    UserService service;

    @Test
    public void getAll() {
        //Given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3;i++){
            users.add(repository.save(factory.user()));
        }

        //When
        List<UserDto> dtos = service.getAll();
        for (User user: users) {
            repository.delete(user);
        }

        //Then
        for (User user: users) {
            assertFalse(repository.existsById(user.getId()));
        }
        assertFalse(dtos.isEmpty());

    }

    @Test
    public void update() {
        //Given
        User user = repository.save(factory.user());
        UserDto dto = mapper.mapToDto(user);
        dto.setEmile("update@emile.com");
        dto.setBalance(-650);

        //When
        UserDto updated = service.update(dto);
        repository.deleteById(user.getId());

        //Then
        assertEquals(user.getId(),updated.getId());
        assertEquals("update@emile.com",updated.getEmile());
        assertEquals(-650,updated.getBalance(),0);
        assertFalse(repository.existsById(updated.getId()));
    }
}