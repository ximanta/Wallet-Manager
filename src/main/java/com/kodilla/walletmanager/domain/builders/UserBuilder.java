package com.kodilla.walletmanager.domain.builders;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;

import java.sql.Date;

public final class UserBuilder {
    private Long id;
    private String login;
    private String password;
    private String emile;
    private Date birthDate;
    private boolean active;
    private CurrencyType currencyType;
    private double balance;

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder emile(String emile) {
        this.emile = emile;
        return this;
    }

    public UserBuilder birthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder currencyType(CurrencyType type) {
        this.currencyType = type;
        return this;
    }

    public UserBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public UserBuilder balance(double balance) {
        this.balance = balance;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmile(emile);
        user.setBirthDate(birthDate);
        user.setActive(active);
        user.setCurrencyType(currencyType);
        user.setBalance(balance);

        return user;
    }
}