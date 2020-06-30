package com.kodilla.walletmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TRANSACTION")
public class Transaction extends GenericEntity {

    @Column
    @NotBlank
    public String title;

    @Column
    public String description;

    @Column
    public long amount;
}