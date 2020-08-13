package com.kodilla.walletmanager.domain.entities;

import com.kodilla.walletmanager.domain.enums.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
@NamedQuery(
        name = "User.get",
        query = "FROM User WHERE login like :LOGIN and password like :PASSWORD")
@NamedQuery(
        name = "User.getByLogin",
        query = "FROM User WHERE login like :LOGIN")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Email(message = "Email should be valid")
    @Column
    private String emile;

    @NotNull
    @Column
    private Date birthDate;

    @NotNull
    @Column
    private boolean active;

    @NotNull
    @Column
    private CurrencyType currencyType;

    @NotNull
    @Column(precision = 2, length = 25, scale = 2)
    private double balance;

    @OneToMany(
            fetch =  FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Transaction.class,
            mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", emile='" + emile + '\'' +
                ", birthDate=" + birthDate +
                ", active=" + active +
                ", currencyType=" + currencyType +
                ", balance=" + balance + '}';
    }
}
