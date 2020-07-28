package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User mapToEntity(UserDto dto){
        User user = new User();
        user.setId(dto.getId());
        user.setEmile(dto.getEmile());
        user.setBalance(dto.getBalance());

        return user;
    }

    public UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmile(user.getEmile());
        dto.setBalance(user.getBalance());

        return dto;
    }

    public List<UserDto> mapToDtos(List<User> users){
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
