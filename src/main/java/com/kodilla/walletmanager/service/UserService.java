package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.User;
import com.kodilla.walletmanager.dto.UserDto;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserMapper mapper;

    @Autowired
    UserRepository repository;

    public List<UserDto> getAll(){
        List<User> users = repository.findAll();
        return mapper.mapToDtos(users);
    }

    public UserDto update(UserDto dto){
        return mapper.mapToDto(updateMechanic(dto));
    }

    private User updateMechanic(UserDto dto){
        Optional<User> optional = repository.findById(dto.getId());
        boolean isPresent = optional.isPresent();
        boolean isEmile = dto.getEmile() != null;
        if (isPresent && isEmile){
            User user = new User();
            user.setId(dto.getId());
            user.setBalance(dto.getBalance());
            user.setEmile(dto.getEmile());
            return repository.save(user);
        } else {
            throw new RuntimeException("Cannot find User by id");
        }
    }

}
