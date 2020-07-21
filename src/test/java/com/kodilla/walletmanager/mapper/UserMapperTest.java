package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.User;
import com.kodilla.walletmanager.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMapper mapper;

    @Test
    public void mapToEntity() {
        //Given
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setBalance(-150.0);
        dto.setEmile("test@gmail.com");

        //When
        User user = mapper.mapToEntity(dto);

        //Then
        assertEquals(1L,user.getId(),0);
        assertEquals(-150,user.getBalance(),0);
        assertEquals("test@gmail.com",user.getEmile());
    }

    @Test
    public void mapToDto() {
        //Given
        User user = createUser();

        //When
        UserDto dto = mapper.mapToDto(user);

        //Then
        assertEquals(1L,dto.getId(),0);
        assertEquals(-150,dto.getBalance(),0);
        assertEquals("test@gmail.com",dto.getEmile());
    }

    @Test
    public void mapToDtos() {
        //Given
        List<User> users = new ArrayList<>();
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());

        //When
        List<UserDto> dtos = mapper.mapToDtos(users);
        UserDto dto = dtos.get(0);

        //Then
        assertEquals(1L,dto.getId(),0);
        assertEquals(-150,dto.getBalance(),0);
        assertEquals("test@gmail.com",dto.getEmile());
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

    private User createUser(){
        User user = new User();
        user.setId(1L);
        user.setBalance(-150.0);
        user.setEmile("test@gmail.com");

        return user;
    }
}