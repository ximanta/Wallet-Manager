package com.kodilla.walletmanager.domain.builders;

import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.enums.CurrencyType;

import java.sql.Date;

public final class UserDtoBuilder {
    private Long id;
    private String login;
    private String password;
    private String emile;
    private Date birthDate;
    private boolean active;
    private CurrencyType currencyType;
    private double balance;

    public UserDtoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserDtoBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UserDtoBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserDtoBuilder emile(String emile) {
        this.emile = emile;
        return this;
    }

    public UserDtoBuilder birthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserDtoBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public UserDtoBuilder currencyType(CurrencyType type){
        this.currencyType = type;
        return this;
    }

    public UserDtoBuilder balance(double balance) {
        this.balance = balance;
        return this;
    }

    public UserDto build() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setLogin(login);
        userDto.setPassword(password);
        userDto.setEmile(emile);
        userDto.setBirthDate(birthDate);
        userDto.setActive(active);
        userDto.setCurrencyType(currencyType);
        userDto.setBalance(balance);

        return userDto;
    }
}