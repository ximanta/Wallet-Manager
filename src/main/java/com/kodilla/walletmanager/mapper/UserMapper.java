package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.builders.UserBuilder;
import com.kodilla.walletmanager.domain.builders.UserDtoBuilder;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToEntity(UserDto dto){
        return new UserBuilder()
                .id(dto.getId())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .emile(dto.getEmile())
                .birthDate(dto.getBirthDate())
                .active(dto.isActive())
                .currencyType(dto.getCurrencyType())
                .balance(dto.getBalance()).build();
    }

    public UserDto mapToDto(User user){
        return new UserDtoBuilder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .emile(user.getEmile())
                .birthDate(user.getBirthDate())
                .active(user.isActive())
                .currencyType(user.getCurrencyType())
                .balance(user.getBalance()).build();
    }

    public List<UserDto> mapToDtos(List<User> users){
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
