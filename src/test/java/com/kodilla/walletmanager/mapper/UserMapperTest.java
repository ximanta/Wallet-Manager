package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
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
        assertEquals(1L,user.getId(),0);
        assertEquals(-150,user.getBalance(),0);
        assertEquals("test@email.com",user.getEmile());
    }

    @Test
    public void mapToDto() {
        //Given
        User user = factory.user();
        user.setId(1L);

        //When
        UserDto dto = mapper.mapToDto(user);


        //Then
        assertEquals(1L,dto.getId(),0);
        assertEquals(-150,dto.getBalance(),0);
        assertEquals("test@email.com",dto.getEmile());
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
        assertEquals(-150,dto.getBalance(),0);
        assertEquals("test@email.com",dto.getEmile());
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