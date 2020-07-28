package com.kodilla.walletmanager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private String login;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private String emile;

    @NotNull
    @Column
    private Date birthDate;

    @NotNull
    @Column
    private boolean active;

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
                ", balance=" + balance +
                '}';
    }
}
