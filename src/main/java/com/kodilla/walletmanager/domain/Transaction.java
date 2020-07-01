package com.kodilla.walletmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column
    private String description;

    @Column
    private double amount;

    public void setAmount(double amount) {
        if (amount < 0){
            System.out.println("Amount cannot be negative");
            this.amount = 0;
        }
        else if((amount * 100) - (long)(amount * 100) != 0) {
            double tmp = Math.round(amount) * 100;
            this.amount = tmp / 100 ;
        }
        else {
            this.amount = amount;
        }
    }
}