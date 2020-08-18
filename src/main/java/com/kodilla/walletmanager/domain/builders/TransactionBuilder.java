package com.kodilla.walletmanager.domain.builders;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.enums.CurrencyType;

import java.sql.Date;

public class TransactionBuilder {
    private Long id;
    private Date date = new Date(new java.util.Date().getTime());
    private String title;
    private String description;
    private CurrencyType currencyType;
    private double amount;
    private Category category;
    private User user;

    public TransactionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TransactionBuilder date(Date date) {
        this.date = date;
        return this;
    }

    public TransactionBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TransactionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TransactionBuilder currencyType(CurrencyType type) {
        this.currencyType = type;
        return this;
    }

    public TransactionBuilder amount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public TransactionBuilder user(User user) {
        this.user = user;
        return this;
    }

    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDate(date);
        transaction.setTitle(title);
        transaction.setDescription(description);
        transaction.setCurrencyType(currencyType);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setUser(user);

        return transaction;
    }
}