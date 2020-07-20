package com.kodilla.walletmanager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private String emile;

    @NotNull
    @Column(precision = 2, length = 25, scale = 2)
    private double balance;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emile='" + emile + '\'' +
                ", balance=" + balance +
                '}';
    }
}
