package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    public void get() {
        //Given
        User fromDb = saveDuplicate(factory.user());

        //When
        UserDto userDto = service.get(fromDb.getLogin(),fromDb.getPassword());
        repository.delete(mapper.mapToEntity(userDto));

        //Then
        assertNotNull(userDto.getId());
        assertTrue(userDto.isActive());
        assertFalse(repository.existsById(userDto.getId()));
        assertEquals("Test",userDto.getLogin());
        assertEquals("Password",userDto.getPassword());
        assertEquals("test@email.com",userDto.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),userDto.getBirthDate());
        assertEquals(CurrencyType.USD,userDto.getCurrencyType());
        assertEquals(-150,userDto.getBalance(),0);
    }

    @Test(expected = RuntimeException.class)
    public void getDefunctUser(){
        service.get("Valid","Valid");
    }

   @Test
    public void getAll() {
        //Given
        saveDuplicate(factory.user());

        //When
        List<UserDto> users = service.getAll();

        //Then
        assertFalse(users.isEmpty());
    }

    @Test
    public void create() {
        //Given
        UserDto userDto = factory.userDto();
        userDto.setLogin("Test 2");

        //When
        UserDto fromDb = service.create(userDto);
        repository.deleteById(fromDb.getId());

        //Then
        assertNotNull(fromDb.getId());
        assertTrue(fromDb.isActive());
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals("Test 2",fromDb.getLogin());
        assertEquals("Password",fromDb.getPassword());
        assertEquals("test@email.com",fromDb.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),fromDb.getBirthDate());
        assertEquals(CurrencyType.USD,fromDb.getCurrencyType());
        assertEquals(-150,fromDb.getBalance(),0);
    }

    @Test(expected = RuntimeException.class)
    public void createValidUser(){
        service.create(new UserDto());
    }

    @Test
    public void update() {
        //Given
        User user = saveDuplicate(factory.user());
        UserDto userDto = factory.userDto();
        userDto.setId(user.getId());
        userDto.setLogin("Update");
        userDto.setPassword("Update");
        userDto.setEmile("update@emile.com");
        userDto.setCurrencyType(CurrencyType.BRL);
        userDto.setActive(false);
        userDto.setBalance(140);

        //When
        UserDto fromDb = service.update(userDto, true);
        repository.delete(mapper.mapToEntity(fromDb));

        //Then
        assertNotNull(fromDb.getId());
        assertFalse(fromDb.isActive());
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals("Update", fromDb.getLogin());
        assertEquals("Update", fromDb.getPassword());
        assertEquals("update@emile.com", fromDb.getEmile());
        assertEquals(Date.valueOf("2000-02-20"), fromDb.getBirthDate());
        assertEquals(CurrencyType.BRL, fromDb.getCurrencyType());
        assertNotEquals(140, fromDb.getBalance(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void validUpdate() {
        User user = saveDuplicate(factory.user());
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        service.update(dto, true);
    }

    @Test
    public void delete() {
        //Given
        User user = saveDuplicate(factory.user());

        //When
        service.delete(user.getId(),user.getPassword());

        //Then
        assertFalse(repository.existsById(user.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void deleteValidId() {
        service.delete(-100,"");
    }

    private User saveDuplicate(User user) {
        Optional<User> optional = repository.getByLogin(user.getLogin());
        return optional.orElseGet(() -> repository.save(user));
    }
}