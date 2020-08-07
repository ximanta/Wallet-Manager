package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.client.ExternalAPIsClient;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.json.CurrencyJson;
import com.kodilla.walletmanager.json.RatesJson;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            LOGGER.error("Valid body");
            throw new RuntimeException("Valid body");
        }
    }

    public UserDto update(UserDto dto, boolean conversion){
        return updateMechanic(dto,conversion);
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
            User user = updated;
            double balance = ToolsManager.tenthRoundDouble(original.getBalance() * getCheckCurrencyValues(original,updated));
            updated.setBalance(balance);
            LOGGER.info("Currency has been converted to : " + updated.getCurrencyType().toString());
            return user;
       }
        return updated;
    }

    private double getCheckCurrencyValues(User original, User updated){
        try {
            CurrencyJson json = client.getCurrenciesValues(updated.getCurrencyType());
            Method method = RatesJson.class.getMethod("get" + original.getCurrencyType().toString());
            double a = (Double) method.invoke(json.getRates());
            LOGGER.info("GetValues from external Api complete");
            return a;
        }catch (Exception e){
            LOGGER.error("GetValues from ExternalApi Error");
            return 1;
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