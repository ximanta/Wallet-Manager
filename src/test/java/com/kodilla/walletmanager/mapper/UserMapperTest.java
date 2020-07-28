package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void mapToEntity() {
        //Given
        UserDto dto = factory.userDto();
        dto.setId(1L);

        //When
        User user = mapper.mapToEntity(dto);

        //Then
        assertTrue(user.isActive());
        assertEquals(1L,user.getId(),0);
        assertEquals("Test",user.getLogin());
        assertEquals("Password",user.getPassword());
        assertEquals("test@email.com",user.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),user.getBirthDate());
        assertEquals(-150.0,user.getBalance(),0);
    }

    @Test
    public void mapToDto() {
        //Given
        User user = factory.user();
        user.setId(1L);

        //When
        UserDto dto = mapper.mapToDto(user);

        //Then
        assertTrue(dto.isActive());
        assertEquals(1L,dto.getId(),0);
        assertEquals("Test",dto.getLogin());
        assertEquals("Password",dto.getPassword());
        assertEquals("test@email.com",dto.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),dto.getBirthDate());
        assertEquals(-150.0,dto.getBalance(),0);
    }

    @Test
    public void mapToDtos() {
        //Given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3; i++){
            users.add(factory.user());
        }

        //When
        List<UserDto> dtos = mapper.mapToDtos(users);
        UserDto dto = dtos.get(0);

        //Then
        assertTrue(dto.isActive());
        assertNull(dto.getId());
        assertEquals("Test",dto.getLogin());
        assertEquals("Password",dto.getPassword());
        assertEquals("test@email.com",dto.getEmile());
        assertEquals(Date.valueOf("2000-02-20"),dto.getBirthDate());
        assertEquals(-150.0,dto.getBalance(),0);
        assertEquals(3,dtos.size());
    }

    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<User> users = new ArrayList<>();

        //When
        List<UserDto> dtos = mapper.mapToDtos(users);

        //Then
        assertTrue(dtos.isEmpty());
    }
}