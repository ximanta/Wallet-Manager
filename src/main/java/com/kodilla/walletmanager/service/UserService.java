package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserMapper mapper;
    private UserRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    private UserService(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public UserDto get(String login,String password){
        Optional<User> user = repository.get(login,password);
        if (user.isPresent()){
            return mapper.mapToDto(user.get());
        }else {
            throw new RuntimeException("Cannot find User by id");
        }
    }

    public List<UserDto> getAll(){
        return mapper.mapToDtos(repository.findAll());
    }

    public UserDto create(UserDto dto){
        if (ToolsManager.isUserDtoCorrect(dto)){
            User user = mapper.mapToEntity(dto);
            User fromDb = checkUserSave(user);
            return mapper.mapToDto(fromDb);
        }else {
            throw new RuntimeException();
        }
    }

    public UserDto update(UserDto dto){
        if (ToolsManager.isUserDtoCorrect(dto)){
            return updateMechanic(dto);
        }else {
            throw new RuntimeException();
        }
    }

    public boolean delete(long id){
        Optional<User> user = repository.findById(id);
        if(user.isPresent()){
            repository.delete(user.get());
            LOGGER.info("User has been deleted");
            return !repository.existsById(id);
        }else {
            throw new RuntimeException("Cannot find User by id");
        }
    }

    private UserDto updateMechanic(UserDto dto){
        if (repository.existsById(dto.getId())){
            User user = mapper.mapToEntity(dto);
            User formDb = checkUserSave(user);
            return mapper.mapToDto(formDb);
        } else {
            LOGGER.error("Cannot find User by id");
            throw new RuntimeException();
        }
    }

    private User checkUserSave(User user){
        try{
            User formDb = repository.save(isExistingUser(user));
            LOGGER.info("User has been saved");
            return formDb;
        }catch (Exception e){
            LOGGER.error("Save error", e);
            throw new RuntimeException();
        }
    }

    private User isExistingUser(User user){
        if (!repository.getByLogin(user.getLogin()).isPresent()){
            LOGGER.info("There is no duplicate user Login");
            return user;
        }else {
            LOGGER.error("There is user with the same login");
            throw new RuntimeException();
        }
    }

}
