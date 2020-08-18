package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.pojos.ConvertCurrency;
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
    private ExternalAPIsClient client;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    public UserService(UserMapper mapper, UserRepository repository, ExternalAPIsClient client) {
        this.mapper = mapper;
        this.repository = repository;
        this.client = client;
    }

    public UserDto get(String login, String password){
        Optional<User> user = repository.get(login,password);
        if (user.isPresent()){
            LOGGER.info("User has been loaded");
            return mapper.mapToDto(user.get());
        }else {
            LOGGER.info("Cannot find User by id");
            throw new RuntimeException("Cannot find User by id");
        }
    }

    public List<UserDto> getAll(){
        return mapper.mapToDtos(repository.findAll());
    }

    public UserDto create(UserDto dto){
        if (ToolsManager.isUserDtoCorrect(dto)){
            User user = mapper.mapToEntity(dto);
            User fromDb = checkUserSave(isExistingUser(user));
            return mapper.mapToDto(fromDb);
        }else {
            LOGGER.error("Valid body");
            throw new RuntimeException("Valid body");
        }
    }

    public UserDto update(UserDto dto, boolean conversion){
        return updateMechanic(dto,conversion);
    }

    public boolean delete(long id, String password){
        Optional<User> user = repository.findById(id);
        if(user.isPresent()){
            if (user.get().getPassword().equals(password)){
                repository.delete(user.get());
                boolean isExisting = repository.existsById(user.get().getId());
                return checkIsExisting(isExisting);
            } else{
                LOGGER.error("Incorrect password");
                throw new RuntimeException("Incorrect password");
            }
        }else {
            LOGGER.error("Cannot find User by id");
            throw new RuntimeException("Cannot find User by id");
        }
    }

    private UserDto updateMechanic(UserDto dto, boolean conversion){
        Optional<User> optional = repository.findById(dto.getId());
        if (optional.isPresent()){
            User user = mapper.mapToEntity(dto);
            User checkCurrency = checkCurrency(optional.get(),user,conversion);
            User formDb = checkUserSave(checkCurrency);
            return mapper.mapToDto(formDb);
        } else {
            LOGGER.error("Cannot find User by id");
            throw new RuntimeException();
        }
    }

    private User checkCurrency(User original, User updated, boolean conversion) {
        if (original.getCurrencyType() != updated.getCurrencyType() && conversion){
            ConvertCurrency convertCurrency = new ConvertCurrency(
                    original.getCurrencyType().toString(),
                    updated.getCurrencyType().toString(),
                    updated.getBalance());
            double balance = client.getConvertCurrency(convertCurrency);
            updated.setBalance(balance);
            LOGGER.info("Currency has been converted to : " + updated.getCurrencyType().toString());
            return updated;
       }
        return updated;
    }

    private User checkUserSave(User user){
        try{
            User formDb = repository.save(user);
            LOGGER.info("User has been saved");
            return formDb;
        }catch (Exception e){
            LOGGER.error("Save error");
            throw new RuntimeException("Save error");
        }
    }

    private User isExistingUser(User user){
        if (!repository.getByLogin(user.getLogin()).isPresent()){
            LOGGER.info("There is no duplicate user Login");
            return user;
        }else {
            LOGGER.error("There is user with the same login");
            throw new RuntimeException("There is user with the same login");
        }
    }

    private boolean checkIsExisting(boolean b){
        if (b){
            LOGGER.warn("User hasn't been deleted");
            return false;
        }
        LOGGER.info("User has been deleted");
        return true;
    }
}